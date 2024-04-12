package ca.thymetodine.models

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
    @SerialName("created_date")
    val createdDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    @SerialName("updated_date")
    var updatedDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val tags: List<String> = Collections.emptyList(),
    val images: String,
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
data class Serving (
    @SerialName("total_servings")
    val totalServings: Int,
    @SerialName("serving_size")
    val servingSize: Int,
    val amount: String
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
    val measurement: String?
)

@Serializable
data class InstructionSection (
    @SerialName("section_name")
    val sectionName: String?,
    val steps: List<String>
)

@Serializable
data class NutritionFacts (
    val calories: Int,
    val protein: Int,
    val carbohydrate: Int,
    val fat: Int,
    @SerialName("saturated_fat")
    val saturatedFat: Int,
    @SerialName("trans_fat")
    val transFat: Int,
    val fibre: Int,
    val sugars: Int,
    val cholesterol: Int,
    val sodium: Int,
    @SerialName("vitamin_d")
    val vitaminD: Int,
    val iron: Int,
    val potassium: Int,
    val calcium: Int
)