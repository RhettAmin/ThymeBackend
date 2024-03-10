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
    @JsonSerialize(using = ToStringSerializer::class)
    val id: String,
    @NotNull
    val name: String? = "",
    val description: String? = "",
    val tags: List<String>? = emptyList(),
    @Contextual
    val image: String,
    @Field("ingredient_section")
    val ingredientSection: List<IngredientSection>? = emptyList(),
    val servings: String? = "",
    @Field("time_to_plate")
    val timeToPlate: Int = 0,
    @Field("instruction_section")
    val instructionSection: List<InstructionSection>? = emptyList(),
    @Field("nutrition_facts")
    val nutritionFacts: NutritionFacts?
)

@Serializable
data class IngredientSection (
    @Field("section_name")
    val sectionName: String?,
    val ingredients: List<Ingredient>
)
@Serializable
data class Ingredient (
    val name: String,
    val quantity: Float,
    val measurement: String?
)

@Serializable
data class InstructionSection (
    @Field("section_name")
    val sectionName: String?,
    val steps: List<String>
)

@Serializable
data class NutritionFacts (
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fats: Int
)

