package com.japharr.keycloakadmin.controller


import com.japharr.keycloakadmin.model.dto.RegisterDto
import com.japharr.keycloakadmin.service.user.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/register")
class RegisterController (val userService: UserService) {
  @PostMapping("/user")
  fun userRegister(@Valid @RequestBody dto: RegisterDto): Mono<String> {
    return userService.register(dto, setOf("USER"))
  }

  @PostMapping("/merchant")
  fun merchantRegister(@Valid @RequestBody dto: RegisterDto): Mono<String> {
    return userService.register(dto, setOf("USER", "MERCHANT"))
  }
}