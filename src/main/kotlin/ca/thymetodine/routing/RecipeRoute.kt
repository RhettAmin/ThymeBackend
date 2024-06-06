package ca.thymetodine.routing

import ca.thymetodine.db.MongoConnection.deleteRecipe
import ca.thymetodine.db.MongoConnection.getRecipes
import ca.thymetodine.db.MongoConnection.postRecipe
import ca.thymetodine.db.MongoConnection.replaceRecipe
import ca.thymetodine.models.Recipe
import ca.thymetodine.models.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.serialization.json.Json

fun Route.recipeRouting() {
    route("/recipes") {
        get {

            val recipeList = getRecipes(call.request.queryParameters["recipeId"], call.request.queryParameters["limit"])

            val statusCode = if (recipeList.isEmpty()) { HttpStatusCode.NotFound } else { HttpStatusCode.OK }
            call.respond(HttpStatusCode.OK, Response(null, recipeList, statusCode.value.toString()))
        }
        post {
            // Grab Request Body and decode
            val newRecipe = Json.decodeFromString<Recipe>(call.receive())

            val response = postRecipe(newRecipe)
            if (response != null) {
                call.respond(Response(response, null, HttpStatusCode.Created.value.toString()))
            } else {
                call.respond(
                    Response("Recipe Creation encountered an issue",
                        null, HttpStatusCode.BadRequest.value.toString()))
            }

        }
        patch {
            /*
                Assumption that for the update call the entire new request is provided with all changes
                This update will completely replace the old value
             */
            // Grab Request Body of recipe we're updating and decode
            val recipeToUpdate = Json.decodeFromString<Recipe>(call.receive())
            recipeToUpdate.updatedDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

            // Call update Call, if the recipe_id is not found it will return a 404 error
            val updatedRecipe = replaceRecipe(recipeToUpdate)
            if (updatedRecipe != null) {
                call.respond(
                    Response(updatedRecipe, null, HttpStatusCode.OK.value.toString()))
            } else {
                call.respond(
                    Response("Recipe Creation encountered an issue",
                        null, HttpStatusCode.BadRequest.value.toString()))
            }
        }
        delete {
            val id = call.request.queryParameters["recipe_id"]
            println("id: $id")
            if (id.isNullOrEmpty()) {
                call.respond(Response("error", null, HttpStatusCode.BadRequest.toString()))
            } else {
                println("id: $id")
                deleteRecipe(id)
                call.respond(Response("Recipe successfully deleted", null, HttpStatusCode.OK.value.toString()))
            }


        }
    }

}