package com.example.healthc.data.dto.recipe

import com.example.healthc.domain.model.recipe.Recipe

data class RecipeDto(
    val id: Int,
    val image: String,
    val imageType: String,
    val likes: Int,
    val title: String,
){
    fun toRecipe() : Recipe = Recipe(
        id = id,
        image = image,
        imageType = imageType,
        likes = likes,
        title = title
    )
}
