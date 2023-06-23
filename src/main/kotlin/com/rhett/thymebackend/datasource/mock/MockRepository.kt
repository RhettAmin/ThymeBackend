package com.rhett.thymebackend.datasource.mock

/*
@Repository
internal class MockRepository: RecipeRepository {

    val recipes = mutableListOf(
        Recipe("1", "recipe1", "cheesy goodness"),
        Recipe("2", "recipe2", "mushroom mania", ingredients = "mushroom, onions, pcikles"),
        Recipe("3", "recipe3", "yeast beast bread", tags = listOf("gluten", "baking"))
    )

    override fun retrieveRecipes(): Collection<Recipe> {
        return recipes
    }

    override fun retrieveRecipe(id: String): Recipe =
        recipes.firstOrNull() { it.id == id }
            ?: throw NoSuchElementException("Could not find recipe with id: $id")

    override fun createRecipe(recipe: Recipe): Recipe {
        if (recipes.any{ it.name == recipe.name || it.id == recipe.id } ) {
            throw IllegalArgumentException("Recipe with the name ${recipe.name} already exists!")
        }
        recipes.add(recipe)
        return recipe
    }

    override fun updateRecipe(updatedRecipe: Recipe): Recipe {
        val currentRecipe = recipes.firstOrNull { it.id == updatedRecipe.id }
            ?: throw NoSuchElementException("Recipe with the id ${updatedRecipe.id} doesn't exist!")

        recipes.remove(currentRecipe)
        recipes.add(updatedRecipe)
        return updatedRecipe
    }

    override fun deleteRecipe(id: String) {
        var recipeToRemove = recipes.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Recipe with the id ${id} doesn't exist!")
        recipes.remove(recipeToRemove)
    }

}

 */