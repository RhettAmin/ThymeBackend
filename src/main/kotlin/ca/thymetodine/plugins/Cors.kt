package ca.thymetodine.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCors() {
    install(CORS) {
        allowHost("localhost:5173")
        allowHost("localhost:8081")
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.ContentLength)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
    }
}