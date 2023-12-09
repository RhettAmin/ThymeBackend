package com.rhett.thymebackend.recipe

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository()
interface RecipeRepository: MongoRepository<Recipe, String> {

    @Query("{name:'?0'}")
    fun findItemByName(name: String): Optional<Recipe>

}