package com.rhett.thymebackend.datasource.MongoDb

import com.rhett.thymebackend.models.Recipe
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoDBDatasource: MongoRepository<Recipe, String> {

    fun findById(id: ObjectId): Recipe


}