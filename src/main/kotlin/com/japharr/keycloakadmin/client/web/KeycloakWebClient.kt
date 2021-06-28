package com.japharr.keycloakadmin.client.web

import com.japharr.keycloakadmin.client.KeycloakClient
import com.japharr.keycloakadmin.config.properties.KeycloakProp
import com.japharr.keycloakadmin.model.Role
import com.japharr.keycloakadmin.model.dto.UserDto
import com.japharr.keycloakadmin.model.keycloak.Credential
import com.japharr.keycloakadmin.model.keycloak.KeycloakRegister
import com.japharr.keycloakadmin.model.keycloak.KeycloakRole
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@Component
@Profile("web-client")
class KeycloakWebClient (@Qualifier("authorized") private val webClient: WebClient.Builder, private val keycloakProp: KeycloakProp):
  KeycloakClient {
 private val client = webClient.baseUrl("${keycloakProp.authUrl}/admin/realms/${keycloakProp.realm}").build()

  override fun getAllUsers() =
    client.get().uri("/users")
      .retrieve()
      .bodyToFlux(UserDto::class.java)

  override fun getById(id: String) =
    client.get().uri("/users/{id}", id)
      .retrieve()
      .bodyToMono(UserDto::class.java)

  override fun createUser(register: KeycloakRegister) =
    client.post().uri("/users")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(register)
      .retrieve()
      .toEntity(object : ParameterizedTypeReference<Mono<Void>>() {})

  override fun resetPassword(userId: String, register: Credential) =
    client.put().uri("/users/{userId}/reset-password", userId)
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(register)
      .retrieve()
      .toEntity(object : ParameterizedTypeReference<Mono<Void>>() {})

  override fun assignRolesToUser(userId: String, roles: Array<Role>) =
    client.post().uri("/users/{userId}/role-mappings/realm", userId)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(roles)
      .retrieve()
      .toEntity(object : ParameterizedTypeReference<Mono<Void>>() {})

  override fun getRoles() =
    client.get().uri("/roles").retrieve().bodyToFlux(KeycloakRole::class.java)

  override fun getCompositeRoles(name: String) =
    client.get().uri("/roles/{name}/composites", name).retrieve().bodyToFlux(KeycloakRole::class.java)

  override fun getRole(name: String) =
    client.get().uri("/roles/{name}", name).retrieve().bodyToMono(KeycloakRole::class.java)

  override fun createRole(role: KeycloakRole): Mono<ResponseEntity<Mono<Void>>> {
    return client.post().uri("/roles")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(role)
      .retrieve()
      .toEntity(object : ParameterizedTypeReference<Mono<Void>>() {})
  }

  override fun updateRole(name: String, role: Role): Mono<ResponseEntity<Mono<Void>>> {
    return client.put().uri("/roles/{name}", name)
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(role)
      .retrieve()
      .toEntity(object : ParameterizedTypeReference<Mono<Void>>() {})
  }

  override fun addCompositeRoles(name: String, roles: Array<Role>): Mono<ResponseEntity<Mono<Void>>> {
    return client.post().uri("/roles/{name}/composites", name)
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(roles)
      .retrieve()
      .toEntity(object : ParameterizedTypeReference<Mono<Void>>() {})
  }

  override fun removeCompositeRoles(name: String, roles: Array<Role>): Mono<ResponseEntity<Mono<Void>>> {
    return client.method(HttpMethod.DELETE).uri("/roles/{name}/composites", name)
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(roles)
      .retrieve()
      .toEntity(object : ParameterizedTypeReference<Mono<Void>>() {})
  }

  override fun deleteRole(name: String): Mono<Void> {
    return client.delete().uri("/roles/{name}", name)
      .retrieve()
      .bodyToMono(Void::class.java)
  }

}