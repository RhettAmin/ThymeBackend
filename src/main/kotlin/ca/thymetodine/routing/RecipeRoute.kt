package ca.thymetodine.routing

import ca.thymetodine.db.MongoConnection.deleteRecipe
import ca.thymetodine.db.MongoConnection.getRecipes
import ca.thymetodine.db.MongoConnection.postRecipe
import ca.thymetodine.db.MongoConnection.replaceRecipe
import ca.thymetodine.models.Recipe
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun Route.recipeRouting() {
    route("/recipes") {
        get {
            val recipeList = getRecipes(call.request.queryParameters["recipeId"])
           call.respond(recipeList)
        }
        post {
            // Grab Request Body and decode
            val newRecipe = Json.decodeFromString<Recipe>(call.receive())

            // Check if recipeID exists in the DB
            val recipeDB = getRecipes(newRecipe.recipeId)
            if (recipeDB.isEmpty()) {
                // If recipe doesn't exist then we'll add it
                val response = postRecipe(newRecipe)
                if (response != null) {
                    call.respondText(response, status = HttpStatusCode.Created)
                } else {
                    call.respondText("Recipe Creation encountered an issue", status = HttpStatusCode.BadRequest)
                }
            } else {
                // IF recipe does exist then we return back a message of it's existence already
                call.respondText { "recipe with that ID already exists!" }
            }
        }
        patch {
            /*
                Assumption that for the update call the entire new request is provided with all changes
                This update will completely replace the old value
             */
            // Grab Request Body of recipe we're updating and decode
            val updatedRecipe = Json.decodeFromString<Recipe>(call.receive())

            // Call update Call, if the recipe_id is not found it will return a 404 error
            val response = replaceRecipe(updatedRecipe)
            if (response != null) {
                call.respondText(response, status = HttpStatusCode.OK)
            } else {
                call.respondText("Recipe Creation encountered an issue", status = HttpStatusCode.NotFound)
            }
        }
        delete {
            call.request.queryParameters["recipeId"]?.let { it1 -> deleteRecipe(it1) }
            call.respondText("Recipe successfully deleted", status = HttpStatusCode.OK)
        }
    }

}