package ca.thymetodine

import ca.thymetodine.db.MongoConnection.initMongoConfig
import ca.thymetodine.plugins.*
import io.ktor.server.application.*
import kotlinx.coroutines.launch

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    appMonitoring()
    configureSerialization()
    configureRouting()
    configureErrorHandling()
    configureLogging()
    initMongoConfig()
}
