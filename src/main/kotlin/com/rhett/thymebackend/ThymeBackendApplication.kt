package com.rhett.thymebackend

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootConfiguration
@EnableAutoConfiguration
@SpringBootApplication()
class ThymeBackendApplication

fun main(args: Array<String>) {
	runApplication<ThymeBackendApplication>(*args)
}
