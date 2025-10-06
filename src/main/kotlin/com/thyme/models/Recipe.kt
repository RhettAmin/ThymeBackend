package com.thyme.models

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
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
//    val metadata: Metadata?,
    @SerialName("created_date")
    val createdDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    @SerialName("updated_date")
    var updatedDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val tags: List<String> = Collections.emptyList(),
//    val images: String,
    @SerialName("ingredient_section")
    val ingredientSection: List<IngredientSection> = Collections.emptyList(),
    val serving: Serving,
    @SerialName("time_to_plate")
    val timeToPlate: Int = 0,
    @SerialName("instruction_section")
    val instructionSection: List<InstructionSection> = Collections.emptyList(),
    @SerialName("nutrition_facts")
    val nutritionFacts: NutritionFacts
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
    val type: Int?
)

@Serializable
data class InstructionSection (
    @SerialName("section_name")
    val sectionName: String?,
    val metadata: InstructionImageMetadata?,
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
    val protein: Double,
    val carbohydrate: Double,
    val fat: Double,
    @SerialName("saturated_fat")
    val saturatedFat: Double,
    @SerialName("trans_fat")
    val transFat: Double,
    val fibre: Double,
    val sugars: Double,
    val cholesterol: Double,
    val sodium: Double,
    @SerialName("vitamin_d")
    val vitaminD: Double,
    val iron: Double,
    val potassium: Double,
    val calcium: Double
)