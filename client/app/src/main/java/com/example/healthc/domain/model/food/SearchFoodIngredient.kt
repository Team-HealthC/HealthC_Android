package com.example.healthc.domain.model.food

data class SearchFoodIngredient(
    val number: Int,
    val offset: Int,
    val ingredients: List<String>,
    val totalResults: Int
)