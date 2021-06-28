package com.japharr.keycloakadmin.service.role


import com.japharr.keycloakadmin.model.dto.RoleDto
import com.japharr.keycloakadmin.service.oauth.OauthService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RoleServiceImpl (private val oauthService: OauthService): RoleService {
  override fun getRoles() = oauthService.getRoles()

  override fun getRole(name: String) = oauthService.getRoles(name)

  override fun createRole(role: RoleDto): Mono<RoleDto> {
    return oauthService.createRole(role)
  }

  override fun updateRole(name: String, role: RoleDto): Mono<RoleDto> {
    return oauthService.updateRole(name, role)
  }

  override fun deleteRole(name: String): Mono<Void> {
    return oauthService.deleteRole(name)
  }
}