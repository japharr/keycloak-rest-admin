package com.japharr.keycloakadmin.utils

fun String.splitNames(): Pair<String, String?> {
  val names = this.split("\\s".toRegex())

  return Pair(names[0], if (names.size >= 2) names[1] else null)
}