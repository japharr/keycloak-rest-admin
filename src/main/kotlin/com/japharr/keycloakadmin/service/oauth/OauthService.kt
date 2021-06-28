package com.japharr.keycloakadmin.service.oauth

import com.japharr.keycloakadmin.model.Role
import com.japharr.keycloakadmin.model.dto.ChangePasswordDto
import com.japharr.keycloakadmin.model.dto.RegisterDto
import com.japharr.keycloakadmin.model.dto.RoleDto
import com.japharr.keycloakadmin.model.dto.UserDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface OauthService {
  fun getUsers(): Flux<UserDto>
  fun getUser(id: String): Mono<UserDto>
  fun createUser(userDto: RegisterDto, roles: Set<String>): Mono<String>
  fun resetPassword(id: String, dto: ChangePasswordDto): Mono<String>
  fun getRoles(): Flux<RoleDto>
  fun getRoles(name: String): Mono<RoleDto>
  fun createRole(role: RoleDto): Mono<RoleDto>
  fun updateRole(name: String, role: RoleDto): Mono<RoleDto>
  fun convertToActualRoles(roles: Set<String>): Flux<Role>
  fun deleteRole(name: String): Mono<Void>
}