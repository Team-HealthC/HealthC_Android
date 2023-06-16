package com.example.healthc.domain.model.recipe

data class Ingredient(
    val number: Int,
    val offset: Int,
    val ingredients: List<String>,
    val totalResults: Int
)