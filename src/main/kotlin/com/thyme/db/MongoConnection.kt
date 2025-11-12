package com.thyme.db

import com.thyme.models.Recipe
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.server.application.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.bson.conversions.Bson


fun Application.configureMongoConnection() {
    MongoConnection.mongoClient = MongoClient.create( environment.config.property("mongo.uri").getString())
    MongoConnection.foodDb = MongoConnection.mongoClient.getDatabase(environment.config.property("mongo.database").getString())
    MongoConnection.recipeCollection = MongoConnection.foodDb.getCollection(environment.config.property("mongo.collection").getString())
}

object MongoConnection {

    lateinit var mongoClient: MongoClient
    lateinit var foodDb: MongoDatabase
    lateinit var recipeCollection: MongoCollection<Recipe>

    fun closeConnection() = mongoClient.close()

    /**
     * getRecipes
     * -> Will return 1 or many recipes depending on if the input parameter is present or not
     * Input:
     *      recipe_id - the recipe Id of the function
     */
    fun getRecipes(recipeId: String?, limit: String?): List<Recipe> {

        println("getRecipes INPUT: $recipeId -- $limit")

        var returnCollection: List<Recipe>
        println(recipeCollection.codecRegistry)
        runBlocking {
            val filter = if (recipeId != null) {
                Filters.eq("recipe_id", recipeId)
            } else {
                Filters.empty()
            }
            var doc = recipeCollection.find(filter).sort(Sorts.descending("updated_date"))
            if (limit != null) {
                doc = doc.limit(limit.toInt())
            }
            returnCollection = doc.toList()
        }

        println("getRecipes OUTPUT: $returnCollection")
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
    fun deleteRecipe(recipeId: String) {
        println("Deleting: $recipeId")
        runBlocking {
            val filter = Filters.eq("recipe_id", recipeId)
            recipeCollection.deleteOne(filter)
        }
    }

}