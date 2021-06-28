package com.japharr.keycloakadmin.client

import com.japharr.keycloakadmin.model.Role
import com.japharr.keycloakadmin.model.dto.UserDto
import com.japharr.keycloakadmin.model.keycloak.Credential
import com.japharr.keycloakadmin.model.keycloak.KeycloakRegister
import com.japharr.keycloakadmin.model.keycloak.KeycloakRole
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface KeycloakClient {
  fun getAllUsers(): Flux<UserDto>

  fun getById(id: String): Mono<UserDto>

  fun createUser(register: KeycloakRegister): Mono<ResponseEntity<Mono<Void>>>

  fun resetPassword(userId: String, register: Credential): Mono<ResponseEntity<Mono<Void>>>

  fun assignRolesToUser(userId: String, roles: Array<Role>): Mono<ResponseEntity<Mono<Void>>>

  fun getRoles(): Flux<KeycloakRole>

  fun getCompositeRoles(name: String): Flux<KeycloakRole>

  fun getRole(name: String): Mono<KeycloakRole>

  fun createRole(role: KeycloakRole): Mono<ResponseEntity<Mono<Void>>>

  fun updateRole(name: String, role: Role): Mono<ResponseEntity<Mono<Void>>>

  fun addCompositeRoles(name: String, roles: Array<Role>): Mono<ResponseEntity<Mono<Void>>>

  fun removeCompositeRoles(name: String, roles: Array<Role>): Mono<ResponseEntity<Mono<Void>>>

  fun deleteRole(name: String): Mono<Void>
}