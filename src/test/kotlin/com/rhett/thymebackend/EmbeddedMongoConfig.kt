package com.rhett.thymebackend

import com.mongodb.client.MongoClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.mongodb.core.MongoTemplate


@Configuration
@EnableAutoConfiguration
@PropertySource("application-test.properties")
class EmbeddedMongoConfig {
    @Value("\${db.connectionURL}")
    private val databaseURL: String? = null

    @Value("\${db.name}")
    private val databaseName: String? = null
//    @Bean
//    @Throws(Exception::class)
//    fun mongoDbFactory(): MongoDbFactory {
//        println("database url: $databaseURL db name: $databaseName")
//        return SimpleMongoDbFactory(MongoClient(databaseURL), databaseName)
//    }

//    @Bean
//    @Throws(Exception::class)
//    fun mongoTemplate(): MongoTemplate {
//        return MongoTemplate(mongoDbFactory())
//    }
}