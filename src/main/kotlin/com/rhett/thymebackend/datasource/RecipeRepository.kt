package com.rhett.thymebackend.datasource

import com.rhett.thymebackend.models.Recipe
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository()
interface RecipeRepository: MongoRepository<Recipe, String> {

    @Query("{name:'?0'}")
    fun findItemByName(name: String): Optional<Recipe>

    fun existsByName(name: String): Boolean

//    fun retrieveRecipes(): Collection<Recipe>
//    fun retrieveRecipe(id: String): Recipe
//    fun createRecipe(recipe: Recipe): Recipe
//    fun updateRecipe(updatedRecipe: Recipe): Recipe
//    fun deleteRecipe(id: String)
}