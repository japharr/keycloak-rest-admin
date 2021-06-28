package com.japharr.keycloakadmin.controller

import com.japharr.keycloakadmin.model.dto.ChangePasswordDto
import com.japharr.keycloakadmin.service.user.UserService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/users/logged-in")
class LoggedInUserController (val userService: UserService)  {
  @GetMapping("/info")
  fun getLoggedInUser() = userService.findByLoggedIn()

  @PostMapping("/reset-password")
  fun changePassword(@Valid @RequestBody dto: ChangePasswordDto): Mono<String> {
    return userService.resetPassword(dto)
  }
}