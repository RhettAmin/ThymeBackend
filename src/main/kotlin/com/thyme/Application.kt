package com.thyme

import ca.thymetodine.plugins.configureLogging
import com.thyme.db.configureMongoConnection
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    appMonitoring()
    configureSerialization()
    configureRouting()
    configureErrorHandling()
    configureLogging()
    configureMongoConnection()
    configureCors()
    configureHeaders()
}
