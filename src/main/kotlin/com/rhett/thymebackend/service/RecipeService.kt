package com.rhett.thymebackend.service

import com.rhett.thymebackend.datasource.RecipeRepository
import com.rhett.thymebackend.models.Recipe
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class RecipeService(
    val recipeRepo: RecipeRepository
) {
    /**
     * =============================================================
     * GET Operations
     * =============================================================
     */

    fun getRecipeCore(id: String?, name: String?): List<Recipe> {
        return if (id != null) {
            listOfNotNull(getRecipeById(id))
        } else if (name != null) {
            listOfNotNull(getRecipeByName(name))
        } else {
            getRecipes()
        }
    }

    fun getRecipes(): List<Recipe> {
        return recipeRepo.findAll()
    }

    fun getRecipeById(id: String): Recipe? {
        return recipeRepo.findById(id).getOrNull()
            ?: throw NoSuchElementException("recipe with id: $id doesn't exist")
            //?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "recipe with id: $id doesn't exist")
    }

    fun getRecipeByName(name: String): Recipe? {
        return recipeRepo.findItemByName(name).getOrNull()
            ?: throw NoSuchElementException("recipe with id: $name doesn't exist")
           // ?: throw throw ResponseStatusException(HttpStatus.NOT_FOUND, "recipe with name: $name doesn't exist")
    }

    private fun doesRecipeExist(recipe: Recipe): Boolean {
        return recipeRepo.existsByName(recipe.name)
        //return recipeRepo.findItemByName(recipe.name).getOrNull() != null
    }

    /**
     * =============================================================
     * SAVE Operations
     * =============================================================
     */

    fun addRecipe(recipe: Recipe): Recipe? {
        // First check if recipe with the name already exists
        if (doesRecipeExist(recipe)) {
            return null
        }
        return recipeRepo.save(recipe)
    }

    fun updateRecipe(recipe: Recipe): Recipe? {
        if (!doesRecipeExist(recipe)) {
            return null
        }
        val currentRecipe = getRecipeByName(recipe.name)
        println(recipe.id)
        val updatedRecipe = recipe.copy(id = currentRecipe!!.id)
        return recipeRepo.save(updatedRecipe)
    }

    /**
     * =============================================================
     * Removal Operations
     * =============================================================
     */

    fun deleteRecipe(id: String) {
        return recipeRepo.deleteById(id)
    }
}