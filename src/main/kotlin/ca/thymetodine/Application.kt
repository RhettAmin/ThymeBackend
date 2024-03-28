package ca.thymetodine

import ca.thymetodine.db.configureMongoConnection
import ca.thymetodine.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

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
