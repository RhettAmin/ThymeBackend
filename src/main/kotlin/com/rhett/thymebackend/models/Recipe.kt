package com.rhett.thymebackend.models

import jakarta.persistence.GeneratedValue
import lombok.Builder
import lombok.Getter
import lombok.ToString
import org.bson.types.ObjectId
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

@Getter
@Builder
@ToString
@Document(collection = "Recipes")
data class Recipe(
    @Id
    @GeneratedValue
    val id: ObjectId?,
    @NotNull
    val name: String = "",
    val notes: String = "",
    val tags: List<String>? = emptyList(),
    val image: UUID? = UUID.fromString("00000000-0000-0000-0000-000000000000"),
    val ingredients: List<Ingredient> = emptyList(),
    val servings: String? = "",
    val instructions: List<String>? = emptyList(),
    @Field("nutrition_facts")
    val nutritionFacts: NutritionFacts?
)

data class Ingredient (
    val name: String,
    val quantity: Float,
    val weight: String
)

data class NutritionFacts (
    val protein: Float,
    val carbs: Float,
    val fats: Float
)