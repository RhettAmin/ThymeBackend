package com.rhett.thymebackend.datasource

import com.rhett.thymebackend.models.Recipe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
class RecipeRepositoryTest (
    @Autowired val recipeRepository: RecipeRepository
) {

    @Test
    fun `Should check if default object is created` () {
        // Given
        val newRecipe:Recipe

        // When


        // Then


    }

}