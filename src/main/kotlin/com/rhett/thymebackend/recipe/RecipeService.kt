package com.rhett.thymebackend.recipe

import org.springframework.stereotype.Service
import kotlin.NoSuchElementException
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
        return if (id != null && name == null) {
            listOfNotNull(getRecipeById(id))
        } else if (name != null && id == null) {
            listOfNotNull(getRecipeByName(name))
        } else {
            getRecipes()
        }
    }

    fun getRecipes(): MutableList<Recipe> {
        return recipeRepo.findAll()
    }

    fun getRecipeById(id: String): Recipe {
        return recipeRepo.findById(id).getOrNull()
            ?: throw NoSuchElementException("recipe with id: $id doesn't exist")
    }

    fun getRecipeByName(name: String): Recipe {
        return recipeRepo.findItemByName(name).getOrNull()
            ?: throw NoSuchElementException("recipe with name: $name doesn't exist")
    }

    /**
     * =============================================================
     * SAVE Operations
     * =============================================================
     */

    fun addRecipe(recipe: Recipe): Recipe? {
        // First check if recipe with the name already exists
        if (recipeRepo.existsById(recipe.id.toString())) {
            return null
        }
        return recipeRepo.save(recipe)
    }

    fun updateRecipe(recipe: Recipe): Recipe? {
        val currentRecipe = getRecipeById(recipe.id.toString()) ?: return null
        val updatedRecipe = recipe.copy(
            id = currentRecipe.id,
            name = recipe.name ?: currentRecipe.name,
            description = recipe.description ?: currentRecipe.description,
            tags = recipe.tags ?: currentRecipe.tags,
            image = recipe.image ?: currentRecipe.image,
            ingredients = recipe.ingredients ?: currentRecipe.ingredients,
            servings = recipe.servings ?: currentRecipe.servings,
            instructions = recipe.instructions ?: currentRecipe.instructions,
            nutritionFacts = recipe.nutritionFacts ?: currentRecipe.nutritionFacts,
        )
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