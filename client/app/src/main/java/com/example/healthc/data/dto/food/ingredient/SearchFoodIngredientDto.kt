package com.example.healthc.data.dto.food.ingredient

import com.example.healthc.domain.model.food.Ingredient
import com.example.healthc.domain.model.food.SearchFoodIngredient

data class SearchFoodIngredientDto(
    val number: Int,
    val offset: Int,
    val results: List<IngredientDto>,
    val totalResults: Int
){
    fun toSearchFoodIngredient() : SearchFoodIngredient = SearchFoodIngredient(
        number = number,
        offset = offset,
        results = results.map{ it.toIngredient() },
        totalResults = totalResults
    )
}

data class IngredientDto(
    val id: Int,
    val image: String,
    val name: String
){
    fun toIngredient() : Ingredient = Ingredient(
        id = id,
        image = image,
        name = name
    )
}