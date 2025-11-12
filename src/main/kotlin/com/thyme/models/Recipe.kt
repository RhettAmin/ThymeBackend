package com.thyme.models

import kotlinx.datetime.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Recipe(
    @SerialName("recipe_id")
    val recipeId: String,
    val name: String = "",
    val description: String = "",
    @SerialName("hero_image_link")
    val heroImageLink: String = "",
    @SerialName("main_image_link")
    val mainImageLink: String = "",
    @SerialName("created_date")
    val createdDate: String = LocalDate.toString(),
    @SerialName("updated_date")
    var updatedDate: String = LocalDate.toString(),
    val tags: List<String> = Collections.emptyList(),
    @SerialName("ingredient_section")
    val ingredientSections: List<IngredientSection> = Collections.emptyList(),
    val serving: Serving,
    @SerialName("time_to_plate")
    val timeToPlate: Int = 0,
    @SerialName("instruction_section")
    val instructionSections: List<InstructionSection> = Collections.emptyList(),
    @SerialName("nutrition_facts")
    val nutritionFacts: NutritionFacts,
    @SerialName("is_active")
    val isActive: Boolean?
)

@Serializable
data class Metadata (
    @SerialName("main_image_alt_text")
    val mainImageAltText: String
)

@Serializable
data class Serving (
    @SerialName("total_servings")
    val totalServings: Int,
    @SerialName("serving_size")
    val servingSize: Int,
    val form: String
)

@Serializable
data class IngredientSection (
    @SerialName("section_name")
    val sectionName: String?,
    val ingredients: List<Ingredient>
)

@Serializable
data class Ingredient (
    val name: String,
    val quantity: Float,
    val measurement: String,
    val type: Int?,
    val nutrients: NutritionFacts?
)

@Serializable
data class InstructionSection (
    @SerialName("section_name")
    val sectionName: String?,
    val metadata: InstructionImageMetadata?,
    @SerialName("image_link")
    val imageLink: String?,
    val steps: List<String>
)

@Serializable
data class InstructionImageMetadata (
    @SerialName("alt_text")
    val altText: String
)

@Serializable
data class NutritionFacts (
    val calories: Double,
    val fat: Double,
    @SerialName("saturated_fat")
    val saturatedFat: Double,
    @SerialName("trans_fat")
    val transFat: Double,
    val carbohydrate: Double,
    val fibre: Double,
    val sugars: Double,
    val protein: Double,
    val cholesterol: Double,
    val sodium: Double,
    @SerialName("vitamin_d")
    val vitaminD: Double,
    val iron: Double,
    val potassium: Double,
    val calcium: Double
)