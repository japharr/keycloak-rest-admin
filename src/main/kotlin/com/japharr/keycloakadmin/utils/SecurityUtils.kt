package com.japharr.keycloakadmin.utils

import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.jwt.Jwt
import reactor.core.publisher.Mono

class SecurityUtils {
  companion object Factory {
    private const val USER_LOGIN_CLAIM_NAME = "preferred_username"

    fun jwt(): Mono<Jwt> {
      return ReactiveSecurityContextHolder.getContext()
        .map { context: SecurityContext ->
          context.authentication.principal
        }.cast(Jwt::class.java)
    }

    fun authorities(): Mono<List<String>>  {
      return ReactiveSecurityContextHolder.getContext()
        .map { context: SecurityContext ->
          context.authentication.authorities.map {
            it.authority
          }.toList()
        }
    }

    fun currentUserLogin(): Mono<String> {
      return jwt().map { jwt: Jwt ->
        jwt.getClaimAsString(USER_LOGIN_CLAIM_NAME)
      }
    }

    fun currentUserSubject(): Mono<String> {
      return jwt().map {it.subject}
    }

    fun hasRole(role: String): Mono<Boolean> {
      return authorities().map {
        println("all user authorities: $it")
        it.contains(role)
      }
    }

    fun hasRoles(roles: Set<String>): Mono<Boolean> {
      return authorities().map {
        it.containsAll(roles)
      }
    }

    fun hasAnyRoles(roles: Set<String>): Mono<Boolean> {
      return authorities().map {
        var itHas = false
        label@for(r in roles) {
          for (j in it) {
            if(j.equals(it)) {
              itHas = true
              break@label
            }
          }
        }
        itHas
      }
    }
  }
}