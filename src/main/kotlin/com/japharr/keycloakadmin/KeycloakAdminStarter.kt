package com.japharr.keycloakadmin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KeycloakAdminStarter

fun main(args: Array<String>) {
	runApplication<KeycloakAdminStarter>(*args)
}
