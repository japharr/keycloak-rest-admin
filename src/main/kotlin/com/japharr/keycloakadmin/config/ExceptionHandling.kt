package com.japharr.keycloakadmin.config

import feign.FeignException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.zalando.problem.spring.webflux.advice.ProblemHandling
import org.zalando.problem.spring.webflux.advice.security.SecurityAdviceTrait

@RestControllerAdvice
class ExceptionHandling: ProblemHandling, SecurityAdviceTrait {
  @ExceptionHandler(WebClientResponseException::class)
  fun handleWebClientResponseException(ex: WebClientResponseException): ResponseEntity<String> {
    return ResponseEntity.status(ex.rawStatusCode).body(ex.responseBodyAsString)
  }

  @ExceptionHandler(FeignException::class)
  fun handleFeignClientResponseException(ex: FeignException): ResponseEntity<String> {
    return ResponseEntity.status(ex.status()).body(ex.contentUTF8())
  }
}