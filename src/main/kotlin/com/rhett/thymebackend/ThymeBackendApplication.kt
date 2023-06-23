package com.rhett.thymebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication()
class ThymeBackendApplication

fun main(args: Array<String>) {
	runApplication<ThymeBackendApplication>(*args)
}
