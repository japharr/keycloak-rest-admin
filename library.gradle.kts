val reactiveFeignVersion by extra { "3.0.3" }
val reactiveFeignCloud2Version by extra { "2.0.31" }
val problemSpringWebfluxVersion by extra { "0.27.0-RC.0" }
val springAutoRestdocsVersion by extra { "2.0.11" }

extra.apply {
  set("springBootWebflux", "org.springframework.boot:spring-boot-starter-webflux")
  set("springBootActuator", "org.springframework.boot:spring-boot-starter-actuator")
  set("springBootOauth2ResourceServer", "org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  set("springBootValidation", "org.springframework.boot:spring-boot-starter-validation")

  set("springSecurityOauth2Jose", "org.springframework.security:spring-security-oauth2-jose")
  set("springSecurityOauth2Client", "org.springframework.security:spring-security-oauth2-client")

  set("jacksonModuleKotlin", "com.fasterxml.jackson.module:jackson-module-kotlin")
  set("reactorKotlinExtension", "io.projectreactor.kotlin:reactor-kotlin-extensions")
  set("kotlinReflect", "org.jetbrains.kotlin:kotlin-reflect")
  set("kotlinStdlibJdk8", "org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  set("kotlinxCoroutinesReactor", "org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

  set("reactiveFeignReactorCloud", "com.playtika.reactivefeign:feign-reactor-cloud:$reactiveFeignVersion")
  set("reactiveFeignReactorCloud2", "com.playtika.reactivefeign:feign-reactor-cloud2:$reactiveFeignCloud2Version")
  set("reactiveFeignReactorWebclient", "com.playtika.reactivefeign:feign-reactor-webclient:$reactiveFeignVersion")
  set("reactiveFeignReactorSpringConfig", "com.playtika.reactivefeign:feign-reactor-spring-configuration:$reactiveFeignVersion")
  set("reactiveFeignReactorSpringCloud", "com.playtika.reactivefeign:feign-reactor-spring-cloud-starter:$reactiveFeignVersion")

  set("problemSpringWebflux", "org.zalando:problem-spring-webflux:$problemSpringWebfluxVersion")

  set("commonsLang3", "org.apache.commons:commons-lang3")

  set("asciidoctor", "org.springframework.restdocs:spring-restdocs-asciidoctor")
  set("springAutoRestdocs", "capital.scalable:spring-auto-restdocs-core:$springAutoRestdocsVersion")
  set("springAutoRestdocsDokkaJson", "capital.scalable:spring-auto-restdocs-dokka-json:$springAutoRestdocsVersion")

  set("springBootDevtools", "org.springframework.boot:spring-boot-devtools")
  set("springBootTest", "org.springframework.boot:spring-boot-starter-test")
  set("reactorTest", "io.projectreactor:reactor-test")
  set("springSecurityTest", "org.springframework.security:spring-security-test")
  set("springRestdocsWebtestclient", "org.springframework.restdocs:spring-restdocs-webtestclient")
}