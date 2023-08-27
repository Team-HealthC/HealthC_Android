package com.example.healthc.data.model.remote.recipe

import com.example.healthc.domain.model.recipe.Ingredient

data class IngredientResultResponse(
    val number: Int,
    val offset: Int,
    val results: List<AllIngredientResponse>,
    val totalResults: Int
)

data class AllIngredientResponse(
    val id: Long,
    val usedIngredientCount: Int,
    val missedIngredientCount: Int,
    val missedIngredients: List<IngredientResponse>,
)

data class IngredientResponse(
    val id: Long,
    val name: String,
    val image: String
){
    fun toDomain() = Ingredient(
        id = id,
        name = name,
        image = image
    )
}