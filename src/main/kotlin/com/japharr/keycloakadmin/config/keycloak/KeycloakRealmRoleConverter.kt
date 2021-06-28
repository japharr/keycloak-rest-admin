package com.japharr.keycloakadmin.config.keycloak

import org.springframework.core.convert.converter.Converter
import java.util.stream.Collectors
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import reactor.core.publisher.Flux


class KeycloakRealmRoleConverter : Converter<Jwt?, Flux<GrantedAuthority>> {
  override fun convert(jwt: Jwt): Flux<GrantedAuthority> {
    val realmAccess: Map<String, Any> = jwt.claims["realm_access"] as Map<String, Any>
    val list = (realmAccess["roles"] as List<String>).stream()
      .map { roleName: String -> "ROLE_$roleName" } // prefix to map to a Spring Security "role"
      .map { role: String? -> SimpleGrantedAuthority(role) }
      .collect(Collectors.toList())

    return Flux.just(*list.toTypedArray())
  }
}