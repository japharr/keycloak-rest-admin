package com.japharr.keycloakadmin.service.oauth

import com.japharr.keycloakadmin.client.KeycloakClient
import com.japharr.keycloakadmin.model.Role
import com.japharr.keycloakadmin.model.dto.ChangePasswordDto
import com.japharr.keycloakadmin.model.dto.RegisterDto
import com.japharr.keycloakadmin.model.dto.RoleDto
import com.japharr.keycloakadmin.model.dto.UserDto
import com.japharr.keycloakadmin.model.keycloak.Credential
import com.japharr.keycloakadmin.model.keycloak.KeycloakRegister
import com.japharr.keycloakadmin.model.keycloak.KeycloakRole
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Collectors


@Service
//@Profile("keycloak")
class OauthServiceImpl (private val keycloakClient: KeycloakClient): OauthService {
  override fun getUsers(): Flux<UserDto> {
    return keycloakClient.getAllUsers()
  }

  override fun getUser(id: String): Mono<UserDto> {
    return keycloakClient.getById(id)
  }

  override fun createUser(userDto: RegisterDto, roles: Set<String>): Mono<String> {
    return keycloakClient.createUser(KeycloakRegister(userDto.email, userDto.name, userDto.password)).flatMap { response ->
      val location: String? =  response.headers.getFirst("Location")
      val userId = location?.substring(location.lastIndexOf("/")+1)

      convertToActualRoles(roles).collectList().flatMap {
        keycloakClient.assignRolesToUser(userId!!, it.toTypedArray()).map {
          userId
        }
      }
    }
  }

  override fun resetPassword(id: String, dto: ChangePasswordDto): Mono<String> {
    return keycloakClient.resetPassword(id, Credential(value = dto.password)).map {
      "Success"
    }
  }

  override fun getRoles() = keycloakClient.getRoles().map {
    RoleDto(it.id, it.name, it.description, it.composite)
  }

  override fun getRoles(name: String): Mono<RoleDto> {
    return keycloakClient.getRole(name).flatMap { role ->
      if(!role.composite) {
        return@flatMap Mono.just(RoleDto(role.id, role.name, role.description, role.composite))
      }

      keycloakClient.getCompositeRoles(role.name).collectList().map { roles ->
        RoleDto(role.id, role.name, role.description, role.composite,
          roles.map { RoleDto(it.id, it.name, it.description, it.composite) }.toSet()
        )
      }
    }
  }

  override fun createRole(role: RoleDto): Mono<RoleDto> {
    return keycloakClient.createRole(KeycloakRole(role.id, role.name, role.description, role.composite))
      .flatMap {
        updateCompositeRoles(role.name, emptySet(), role.compositeRoles, role.composite)
      }.map { role }
  }

  override fun updateRole(name: String, role: RoleDto): Mono<RoleDto> {
    return getRoles(name).flatMap { ext ->
      keycloakClient.updateRole(name, KeycloakRole(role.id, role.name, role.description, role.composite)).flatMap {
        updateCompositeRoles(name, ext.compositeRoles, role.compositeRoles, role.composite, ext.composite)
      }
    }.map { role }
  }

  private fun addCompositeRoles(name: String, compositeRoles: Set<RoleDto>, composite: Boolean): Mono<String> {
    if(!composite || compositeRoles.isEmpty()) return Mono.just("Done!")

    return convertToActualRoles(compositeRoles.map { it.name }.toSet()).collectList().flatMap { roles ->
      keycloakClient.addCompositeRoles(name, roles.toTypedArray())
    }.map { "Success" }
  }

  private fun removeCompositeRoles(name: String, compositeRoles: Set<RoleDto>, composite: Boolean): Mono<String> {
    if(!composite || compositeRoles.isEmpty()) return Mono.just("Done!")

    return convertToActualRoles(compositeRoles.map { it.name }.toSet()).collectList().flatMap { roles ->
      keycloakClient.removeCompositeRoles(name, roles.toTypedArray())
    }.map { "Success" }
  }

  private fun updateCompositeRoles(name: String, extCompositeRoles: Set<RoleDto>, compositeRoles: Set<RoleDto>, composite: Boolean, extComposite: Boolean = false): Mono<String> {
    println("addCompositeRoles: $name")
    if(!composite && !extComposite) return Mono.just("Done!")

    val whatToAdd = compositeRoles subtract extCompositeRoles
    val whatToRemove = extCompositeRoles subtract compositeRoles

    return removeCompositeRoles(name, whatToRemove, extComposite).flatMap {
      addCompositeRoles(name, whatToAdd, composite)
    }.map { "Success" }
  }

  override fun convertToActualRoles(roles: Set<String>): Flux<Role> {
    return keycloakClient.getRoles().collectList().map { r ->
      val existingRoles = r.stream().collect(Collectors.toList())

      val serverRoles: List<String> = existingRoles
        .stream().map { e -> e.name }.collect(Collectors.toList())

      val newRoles = roles.stream()
        .filter { serverRoles.indexOf(it) != -1 }
        .map { existingRoles[serverRoles.indexOf(it)] }
        .collect(Collectors.toList())

      newRoles
    }.flatMapMany{ Flux.fromIterable(it)}
  }

  override fun deleteRole(name: String): Mono<Void> {
    return keycloakClient.deleteRole(name)
  }
}