package com.japharr.keycloakadmin.config


import com.japharr.keycloakadmin.config.properties.KeycloakProp
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager
import org.springframework.web.reactive.function.client.*
import org.springframework.web.server.WebFilter
import reactor.core.publisher.Flux


@Configuration
@Profile("web-client")
class WebClientConfig {
  @Bean
  @Qualifier("authorized")
  fun loadBalancedWebClientBuilder(authorizedClientManager: ReactiveOAuth2AuthorizedClientManager, keycloakProp: KeycloakProp): WebClient.Builder? {
    return WebClient.builder()
      .filter(authorizationFilter(authorizedClientManager, keycloakProp))
  }

  @Bean
  @Order(-1)
  fun webExceptionHandler(): WebFilter? {
    return WebFilter { exchange, next ->
      next.filter(exchange)
        .onErrorResume(WebClientResponseException::class.java) { e ->
          val bytes: ByteArray = e.responseBodyAsByteArray //e.contentUTF8().toByteArray(StandardCharsets.UTF_8)
          val buffer: DataBuffer = exchange.response.bufferFactory().wrap(bytes)

          val response: ServerHttpResponse = exchange.response
          response.statusCode = e.statusCode
          response.writeWith(Flux.just(buffer))
        }
    }
  }

  private fun authorizationFilter(authorizedClientManager: ReactiveOAuth2AuthorizedClientManager, keycloakProp: KeycloakProp): ExchangeFilterFunction {
    return ExchangeFilterFunction { request: ClientRequest, next: ExchangeFunction ->
      val oauth2Request = OAuth2AuthorizeRequest
        .withClientRegistrationId("keycloak") // <- Here you load your registered client
        .principal(keycloakProp.credentials.clientId)
        .build()

      authorizedClientManager.authorize(oauth2Request)
        .flatMap { r ->
          val clientRequest: ClientRequest = ClientRequest.from(request)
            .headers { headers -> headers.set(HttpHeaders.AUTHORIZATION, "Bearer ${r.accessToken.tokenValue}") }
            .build()
          next.exchange(clientRequest)
        }
    }
  }
}