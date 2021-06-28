package com.japharr.keycloakadmin.service.user


import com.japharr.keycloakadmin.model.dto.ChangePasswordDto
import com.japharr.keycloakadmin.model.dto.RegisterDto
import com.japharr.keycloakadmin.model.dto.UserDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserService {
  //fun register(userDto: RegisterDto): Mono<String>
  fun register(userDto: RegisterDto, roles: Set<String>): Mono<String>

  fun findAll(): Flux<UserDto>

  fun findById(id: String): Mono<UserDto>

  fun findByLoggedIn(): Mono<UserDto>

  fun resetPassword(dto: ChangePasswordDto): Mono<String>
}