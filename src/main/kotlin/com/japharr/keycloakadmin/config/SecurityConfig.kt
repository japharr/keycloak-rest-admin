package com.japharr.keycloakadmin.config

import com.japharr.keycloakadmin.config.keycloak.KeycloakRealmRoleConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.client.ClientCredentialsReactiveOAuth2AuthorizedClientProvider
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.zalando.problem.spring.webflux.advice.security.SecurityProblemSupport
import reactor.core.publisher.Mono


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Import(SecurityProblemSupport::class)
class SecurityConfig {
  @Autowired
  lateinit var problemSupport: SecurityProblemSupport

  @Bean
  fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
    http
      .exceptionHandling().authenticationEntryPoint(problemSupport)
      .accessDeniedHandler(problemSupport).and()
      .csrf().disable()
      .cors().configurationSource(createCorsConfigSource()).and()
      .authorizeExchange()
      .pathMatchers("/admin/**").hasRole("ADMIN")
      .pathMatchers(HttpMethod.POST,"/register/**").permitAll()
      .pathMatchers(HttpMethod.POST,"/v1/register/**").permitAll()
      .pathMatchers(HttpMethod.GET,"/.well-known/**").permitAll()
      .anyExchange().authenticated()
      .and()
      .oauth2ResourceServer()
      .jwt()
      .jwtAuthenticationConverter(jwtAuthenticationConverter())

    return http.build()
  }

  private fun jwtAuthenticationConverter(): Converter<Jwt?, Mono<AbstractAuthenticationToken>> {
    val jwtConverter = ReactiveJwtAuthenticationConverter() //JwtAuthenticationConverter()
    jwtConverter.setJwtGrantedAuthoritiesConverter(KeycloakRealmRoleConverter())
    return jwtConverter
  }

  fun createCorsConfigSource(): CorsConfigurationSource? {
    val config = CorsConfiguration()
    config.addAllowedOrigin("http://localhost:5002")

    config.addAllowedMethod("GET")
    config.addAllowedMethod("PUT")
    config.addAllowedMethod("POST")
    config.addAllowedMethod("DELETE")
    config.addAllowedMethod("OPTIONS")

    config.addExposedHeader("Access-Control-Allow-Origin")
    config.addAllowedHeader("Authorization")

    config.allowCredentials = true
    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", config)
    return source
  }

  @Bean
  fun authorizedClientManager(
    clientRegistrationRepository: ReactiveClientRegistrationRepository?,
    authorizedClientRepository: ServerOAuth2AuthorizedClientRepository?
  ): ReactiveOAuth2AuthorizedClientManager? {

    val authorizedClientProvider = ClientCredentialsReactiveOAuth2AuthorizedClientProvider()

//    val authorizedClientProvider: ReactiveOAuth2AuthorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
//      .authorizationCode()
//      .clientCredentials()
//      .build()
    val authorizedClientManager = DefaultReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository)
    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)

    // For the `password` grant, the `username` and `password` are supplied via request parameters,
    // so map it to `OAuth2AuthorizationContext.getAttributes()`.
    //authorizedClientManager.setContextAttributesMapper(contextAttributesMapper())
    return authorizedClientManager
  }
}