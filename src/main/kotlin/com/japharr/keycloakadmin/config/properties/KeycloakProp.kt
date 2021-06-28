package com.japharr.keycloakadmin.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "keycloak", ignoreUnknownFields = false)
data class KeycloakProp(var realm: String = "", var authUrl: String = "", var credentials: Credentials = Credentials()) {
  data class Credentials(var clientId: String = "", var clientSecret: String = "")
}