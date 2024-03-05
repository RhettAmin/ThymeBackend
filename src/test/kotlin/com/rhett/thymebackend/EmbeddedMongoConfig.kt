package com.rhett.thymebackend

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource


//@Configuration
//@EnableAutoConfiguration
//@PropertySource("application.properties")
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