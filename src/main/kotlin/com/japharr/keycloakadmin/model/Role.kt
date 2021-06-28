package com.japharr.keycloakadmin.model

abstract class Role {
  abstract var id: String
  abstract var name: String
  abstract var description: String?
  abstract var composite: Boolean
}