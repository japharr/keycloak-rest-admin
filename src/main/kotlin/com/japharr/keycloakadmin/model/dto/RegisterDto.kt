package com.japharr.keycloakadmin.model.dto

import com.japharr.keycloakadmin.utils.splitNames
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class RegisterDto(
  @field:NotBlank @field:NotEmpty
  var email: String = "",
  @field:NotBlank @field:NotEmpty
  var name: String = "",
  @field:NotBlank @field:NotEmpty
  var password: String
) {
  val username: String get() = email
  val firstName: String get() = name.splitNames().first
  val lastName: String? get() = name.splitNames().second
  val enabled: Boolean get() = true
}