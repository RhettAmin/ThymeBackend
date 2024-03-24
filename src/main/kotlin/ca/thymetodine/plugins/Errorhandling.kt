package ca.thymetodine.plugins

import com.mongodb.MongoException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->

            when (cause) {
                is NotFoundException -> {
                    call.respondText(text = "404: Content Not Found", status = HttpStatusCode.NotFound)
                }
                is UnsupportedMediaTypeException -> {
                    call.respondText(text = "415: Content Not Found: $cause", status = HttpStatusCode.UnsupportedMediaType)
                }
                else -> {
                    call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
                }
            }

        }
    }
}