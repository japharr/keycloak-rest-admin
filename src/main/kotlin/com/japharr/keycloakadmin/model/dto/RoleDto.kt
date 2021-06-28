package com.japharr.keycloakadmin.model.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class RoleDto(
  @JsonIgnore
  var id: String = "",
  @field:NotBlank @field:NotEmpty
  var name: String,
  var description: String? = null,
  var composite: Boolean = false,
  val compositeRoles: Set<RoleDto> = emptySet()
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RoleDto

    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    return name.hashCode()
  }
}