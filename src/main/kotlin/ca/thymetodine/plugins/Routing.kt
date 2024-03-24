package ca.thymetodine.plugins

import ca.thymetodine.routing.recipeRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Application.configureRouting() {
    routing {
        recipeRouting()
        get("/404") {
            call.respondText("404!")
        }
    }
}
