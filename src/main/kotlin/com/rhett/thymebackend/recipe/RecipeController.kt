package com.rhett.thymebackend.recipe

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recipes")
class RecipeController (
    private val service: RecipeService
){
    /**
     * Get Recipe
     *   Function that returns a list of recipe objects.
     *   Passing in an id or name will return that recipe or nothing.
     * Input:
     *   id - ObjectId of the object, auto-created by Mongo
     *   name - Name of the recipe you are looking for
     * Returns:
     *   a list of recipe objects or a single recipe object
     */
    @CrossOrigin
    @GetMapping
    fun getRecipe(@RequestParam(required = false) id: String?,
                  @RequestParam(required = false) name: String?)
    : List<Recipe?> {
        return service.getRecipeCore(id, name)
    }

    @CrossOrigin
    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun addNewRecipe(
        @RequestBody recipe: Recipe)
    : Recipe {
        println(recipe)
        return service.addRecipe(recipe) ?:
            throw IllegalArgumentException("recipe with the name: ${recipe.name} already exists")
    }

    @CrossOrigin
    @PatchMapping
    fun updateRecipe(
        @RequestBody updatedRecipe: Recipe)
    : Recipe? {
       return service.updateRecipe(updatedRecipe) ?:
            throw IllegalArgumentException("recipe with the name: ${updatedRecipe.name} doesn't exist")
    }

    @CrossOrigin
    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    fun deleteRecipe(
        @RequestParam id: String)
    {
        service.deleteRecipe(id)
    }
}