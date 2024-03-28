package ca.thymetodine.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCors() {
    install(CORS) {
        allowHost("127.0.0.1:5173")
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.ContentLength)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
    }
}