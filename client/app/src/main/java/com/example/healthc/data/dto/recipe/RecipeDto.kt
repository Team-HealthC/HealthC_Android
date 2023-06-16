package com.example.healthc.data.dto.recipe

import com.example.healthc.domain.model.recipe.Recipe

data class RecipeDto(
    val id: Int,
    val image: String,
    val imageType: String,
    val likes: Int,
    val missedIngredientCount: Int,
    val missedIngredients: List<MissedIngredientDto>,
    val title: String,
    val unusedIngredients: List<Any>,
    val usedIngredientCount: Int,
    val usedIngredients: List<UsedIngredientDto>
){
    fun toRecipe() : Recipe = Recipe(
        id = id,
        image = image,
        imageType = imageType,
        likes = likes,
        title = title
    )
}

data class UsedIngredientDto(
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