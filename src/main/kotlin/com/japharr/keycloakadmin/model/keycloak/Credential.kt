package com.japharr.keycloakadmin.model.keycloak

data class Credential(
  var value: String,
  var temporary: Boolean = true
) {
  fun getType() = "password"
}