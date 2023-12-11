package com.rhett.thymebackend.recipe

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import jakarta.persistence.GeneratedValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import lombok.Builder
import lombok.Getter
import lombok.Setter
import lombok.ToString
import org.bson.types.ObjectId
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*
import java.util.Collections.emptyList

@Getter
@Setter
@Builder
@ToString
@Serializable
@Document(collection = "Recipes")
data class Recipe(
    @Id
    @GeneratedValue
    @Contextual
    @JsonSerialize(using = ToStringSerializer::class)
    val id: ObjectId?,
    @NotNull
    val name: String? = "",
    val description: String? = "",
    val tags: List<String>? = emptyList(),
    @Contextual
    val image: UUID? = UUID.fromString("00000000-0000-0000-0000-000000000000"),
    val ingredients: List<Ingredient>? = emptyList(),
    val servings: String? = "",
    val instructions: List<Instruction>? = emptyList(),
    @Field("nutrition_facts")
    val nutritionFacts: NutritionFacts?
)

@Serializable
data class Ingredient (
    val name: String,
    val quantity: Float,
    val measurement: String
)

@Serializable
data class Instruction (
    val step: String
)

@Serializable
data class NutritionFacts (
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fats: Int
)

