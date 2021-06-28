package com.japharr.keycloakadmin.service.role

import com.japharr.keycloakadmin.model.dto.RoleDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RoleService {
  fun getRoles(): Flux<RoleDto>

  fun getRole(name: String): Mono<RoleDto>

  fun createRole(role: RoleDto): Mono<RoleDto>

  fun updateRole(name: String, role: RoleDto): Mono<RoleDto>

  fun deleteRole(name: String): Mono<Void>
}