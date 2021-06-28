package com.japharr.keycloakadmin.model.keycloak

import com.japharr.keycloakadmin.utils.splitNames


class KeycloakRegister (
  var email: String,
  var firstName: String,
  var lastName: String?,
  var credentials: Array<Credential> = emptyArray()
) {
  constructor(email: String, name: String, password: String) :
      this(email, name.splitNames().first, name.splitNames().second, arrayOf(Credential(password)))

  val username: String get() = email
  val enabled: Boolean get() = true
  val emailVerified: Boolean get() = true
}
