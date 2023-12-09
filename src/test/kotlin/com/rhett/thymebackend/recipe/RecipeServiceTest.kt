package com.rhett.thymebackend.recipe

import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest


@DataMongoTest
class RecipeServiceTest (
    @Autowired var recipeRepository: RecipeRepository
) {
    private var recipeService = RecipeService(recipeRepository)

    @BeforeAll
    fun setup() {
        val recipe = Recipe(id = ObjectId.get(), name="test", description="test",
            nutritionFacts = NutritionFacts(5f, 5.0f, 5f))
        recipeRepository.save(recipe)
    }

    @AfterAll
    fun exit() {
        //val recipeToDelete = recipeRepository.findItemByName("test")
       // recipeRepository.deleteById(recipeToDelete.get().id.toString())
    }

    @Test
    fun `Should call DB for all Recipes` () {
        // Given
       // every { recipeRepository.findAll() } returns emptyList()

        // When
        val recipes = recipeService.getRecipeCore(null, null)

        // Then
        assertThat(recipes).isNotEmpty
    }

    @Test
    fun `Should call DB to get 1 recipe and get null back` () {
        //Given
        //every { recipeRepository.findById(any()) } returns Optional.empty()

        // When
        val recipe: List<Recipe> = recipeService.getRecipeCore(id="1idw", null)

        // Then
        verify (exactly = 1) { recipeService.getRecipeCore(id="1idw", null) }
        //verify (exactly = 1)  { exceptionHandler.handleNotFoundErrors(any()) }
    }

//    @Test
//    fun `Should save new recipe and check if it exists` () {
//        //Given
//        every { recipeRepositoryImpl.getOneRecipe(any()) } returns null
//        val newRecipe = Recipe(name = "recipe1", notes = "new Recipe")
//
//        // When
//        val recipe: Recipe = recipeService.addRecipe(newRecipe)
//
//        // Then
//        val getRecipe = recipeService.getRecipe(recipe.id.toString())
//        assertThat(getRecipe).isNotNull
//    }

//    @Test
//    fun `Should call data source to create a new recipe` () {
//        // Given
//        val newRecipe = Recipe(ObjectId(), "Yakisoba", notes = "a delicious noodle dish fried in soy sauce")
//
//        // When
//        val recipe: Recipe = recipeService.addRecipe(newRecipe)
//
//        // Then
//        assertThat(recipeService.getRecipes()).isNotEmpty
//        assertThat(recipe.name).isEqualTo(newRecipe.name)
//        assertThat(recipe.notes).isEqualTo(newRecipe.notes)
//
//    }


}