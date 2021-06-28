import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.20"
	kotlin("plugin.spring") version "1.5.20"
}

group = "com.japharr.keycloakadmin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

apply(rootProject.file("library.gradle.kts"))

dependencies {
	implementation("${project.ext["springBootActuator"]}")
	implementation("${project.ext["springBootWebflux"]}")
	implementation("${project.ext["springBootValidation"]}")
	implementation("${project.ext["springSecurityOauth2Client"]}")
	implementation("${project.ext["springBootOauth2ResourceServer"]}")

	implementation("${project.ext["reactiveFeignReactorCloud2"]}") {
		exclude(group = "feign-reactor-cloud", module = "feign-reactor-cloud")
	}
	implementation("${project.ext["reactiveFeignReactorWebclient"]}")
	implementation("${project.ext["reactiveFeignReactorSpringConfig"]}")
	implementation("${project.ext["reactiveFeignReactorSpringCloud"]}")

	implementation("${project.ext["jacksonModuleKotlin"]}")
	implementation("${project.ext["reactorKotlinExtension"]}")
	implementation("${project.ext["kotlinReflect"]}")
	implementation("${project.ext["kotlinStdlibJdk8"]}")
	implementation("${project.ext["kotlinxCoroutinesReactor"]}")

	implementation("${project.ext["problemSpringWebflux"]}")

	developmentOnly("${project.ext["springBootDevtools"]}")
	testImplementation("${project.ext["springBootTest"]}")
	testImplementation("${project.ext["reactorTest"]}")
	implementation(kotlin("stdlib"))

	/*
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	*/
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
