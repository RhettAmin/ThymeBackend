package ca.thymetodine.plugins

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import org.slf4j.LoggerFactory

fun Application.configureLogging() {
    install(CallLogging) {
        level = org.slf4j.event.Level.INFO
        filter { call -> call.request.path().startsWith("TRACE") }
        val loggerContext: LoggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
        val mongoLogger: Logger = loggerContext.getLogger("org.mongodb.driver")
        mongoLogger.level = Level.INFO
    }
}