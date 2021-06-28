package com.japharr.keycloakadmin.model.keycloak

import com.japharr.keycloakadmin.model.Role
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class KeycloakRole(
  override var id: String = "",
  @field:NotBlank @field:NotEmpty
  override var name: String,
  override var description: String? = null,
  override var composite: Boolean = false
): Role() {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as KeycloakRole

    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    return name.hashCode()
  }
}