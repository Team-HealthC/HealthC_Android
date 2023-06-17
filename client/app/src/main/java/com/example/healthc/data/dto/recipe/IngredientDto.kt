package com.example.healthc.data.dto.recipe

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
    val missedIngredients: List<MissedIngredientDto>,
)

data class MissedIngredientDto(
    val name: String,
)

