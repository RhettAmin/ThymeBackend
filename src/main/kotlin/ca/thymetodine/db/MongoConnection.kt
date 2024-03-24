package ca.thymetodine.db

import ca.thymetodine.models.Recipe
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.server.application.*
import io.realm.kotlin.internal.platform.runBlocking
import kotlinx.coroutines.flow.toList
import org.mongodb.kbson.ObjectId

object MongoConnection {

    lateinit var uri: String
    lateinit var mongoClient: MongoClient
    lateinit var foodDb: MongoDatabase
    lateinit var recipeCollection: MongoCollection<Recipe>

    fun Application.initMongoConfig() {
        uri = environment.config.property("mongo.uri").getString()
        mongoClient = MongoClient.create(uri)
        foodDb = mongoClient.getDatabase(environment.config.property("mongo.database").getString())
        recipeCollection = foodDb.getCollection(environment.config.property("mongo.collection").getString())
    }

    fun closeConnection() = mongoClient.close()

    /**
     * getRecipes
     * -> Will return 1 or many recipes depending on if the input parameter is present or not
     * Input:
     *      recipe_id - the recipe Id of the function
     */
    fun getRecipes(recipe_id: String?): List<Recipe> {
        var returnCollection: List<Recipe> = listOf()
        println(recipeCollection.codecRegistry)
        runBlocking {
            val filter = if (recipe_id != null) {
                Filters.eq("recipe_id", recipe_id)
            } else {
                Filters.empty()
            }
            println("filter: $filter")
            val doc = recipeCollection.find(filter)
            returnCollection = doc.toList()
            println(returnCollection)
        }

        return returnCollection
    }

    /**
     * postRecipe
     * -> Will add a new recipe to the DB and return a successful message if it was complete
     * Input:
     *      recipe - the recipe we are adding
     */
    fun postRecipe(recipe: Recipe): String? {
        var response: String? = null
        runBlocking {
            val result = recipeCollection.insertOne(recipe)
            if (result.wasAcknowledged()) {
                response = "Recipe was successfully added!"
            }
        }
        return response
    }

    /**
     * updateRecipe
     * -> Will attempt to find a recipe based on the recipe_id and if it finds one it will replace the document for
     *    the one provided.
     *    If nothing is found it will return a 404
     * Input:
     *      recipe - the recipe we are replacing
     */
    fun replaceRecipe(recipe: Recipe): String? {
        var response: String? = null
        runBlocking {
            val filter = Filters.eq("recipe_id", recipe.recipeId)
            val result = recipeCollection.findOneAndReplace(filter, recipe)
            if (result != null) {
                response = "Recipe was successfully updated!"
            }
        }
        return response
    }

    /**
     * deleteRecipe
     * -> Will remove the recipe with the provided id and return a successful message if it deletes the document
     * Input:
     *      recipe_id - the recipe we are adding
     */
    fun deleteRecipe(recipe_id: String) {
        runBlocking {
            val filter = Filters.eq("recipe_id", recipe_id)
            recipeCollection.deleteOne(filter)
        }
    }

}