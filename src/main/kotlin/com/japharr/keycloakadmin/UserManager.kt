package com.japharr.keycloakadmin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserManager

fun main(args: Array<String>) {
	runApplication<UserManager>(*args)
}
