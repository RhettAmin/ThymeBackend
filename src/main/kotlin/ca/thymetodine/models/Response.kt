package ca.thymetodine.models

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class Response (
    val message: String?,
    val recipeList: List<Recipe>?,
    val statusCode: String
)