package com.japharr.keycloakadmin.controller.admin

import com.japharr.keycloakadmin.service.user.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
class UserController (val userService: UserService) {
  @GetMapping
  fun getAll() = userService.findAll()

  @GetMapping("/{id}")
  fun getById(@PathVariable id: String) = userService.findById(id)
}