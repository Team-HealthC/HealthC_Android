package com.example.healthc.data.dto.ingredient

import com.example.healthc.domain.model.recipe.Ingredient

data class IngredientDto(
    val number: Int,
    val offset: Int,
    val results: List<IngredientResultDto>,
    val totalResults: Int
){
    fun toIngredient() : Ingredient = Ingredient(
        number = number,
        offset = offset,
        ingredients = results.first().missedIngredients.map{ it.name },
        totalResults = totalResults
    )
}

data class IngredientResultDto(
    val id: Int,
    val image: String,
    val imageType: String,
    val likes: Int,
    val missedIngredientCount: Int,
    val missedIngredients: List<MissedIngredientDto>,
    val title: String,
    val unusedIngredients: List<String>,
    val usedIngredientCount: Int,
    val usedIngredients: List<String>
)

data class MissedIngredientDto(
    val aisle: String,
    val amount: Double,
    val extendedName: String,
    val id: Int,
    val image: String,
    val meta: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val unit: String,
    val unitLong: String,
    val unitShort: String
)

