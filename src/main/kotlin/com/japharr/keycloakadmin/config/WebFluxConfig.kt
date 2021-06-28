package com.japharr.keycloakadmin.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebFluxConfig: WebFluxConfigurer {
  @Value("\${spring.messages.basename}")
  private val basename: String? = null

  @Bean
  fun messageSource(): MessageSource {
    val messageSource = ReloadableResourceBundleMessageSource()
    messageSource.setBasename("classpath:$basename")
    messageSource.setCacheSeconds(5)
    messageSource.setUseCodeAsDefaultMessage(true)
    messageSource.setDefaultEncoding("UTF-8")
    return messageSource
  }
}