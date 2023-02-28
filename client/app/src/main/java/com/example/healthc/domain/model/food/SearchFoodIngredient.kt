package com.example.healthc.domain.model.food

data class SearchFoodIngredient(
    val number: Int,
    val offset: Int,
    val results: List<Ingredient>,
    val totalResults: Int
)

data class Ingredient(
    val id: Int,
    val image: String,
    val name: String
)
