package com.japharr.keycloakadmin.model.dto

data class UserDto(
  var id: String,
  var email: String?,
  var username: String,
  var enabled: Boolean,
  var emailVerified: Boolean
)