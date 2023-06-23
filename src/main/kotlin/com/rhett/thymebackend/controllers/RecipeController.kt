package com.rhett.thymebackend.controllers

import com.rhett.thymebackend.models.Recipe
import com.rhett.thymebackend.service.RecipeService
import org.springframework.data.mongodb.repository.Query
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recipes")
class RecipeController (
    private val service: RecipeService
){

    // Error Handlers


    // ============================================================================

    // REST MAPPINGS
//    @GetMapping
//    fun getRecipes() : List<Recipe> {
//        return service.getRecipes()
//    }

    @GetMapping
    fun getRecipe(@RequestParam(required = false) id: String?, @RequestParam(required = false) name: String?): List<Recipe?> {
        return service.getRecipeCore(id, name) ?: throw NoSuchElementException("Recipe with id: $id doesn't exist")
    }
//
//    @GetMapping
//    fun getRecipeByName(@RequestParam name: String): Recipe {
//        return service.getRecipeByName(name) ?:
//            throw NoSuchElementException("Recipe with name: $name doesn't exist")
//    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addNewRecipe(@RequestBody recipe: Recipe): Recipe {
       return service.addRecipe(recipe) ?:
            throw IllegalArgumentException("recipe with the name: ${recipe.name} already exists")
    }

   @PatchMapping
   fun updateRecipe(@RequestBody updatedRecipe: Recipe): Recipe? {
       return service.updateRecipe(updatedRecipe) ?:
            throw IllegalArgumentException("recipe with the name: ${updatedRecipe.name} doesn't exist")
   }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRecipe(@RequestParam id: String) {
        service.deleteRecipe(id)
    }
}