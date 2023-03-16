package com.example.healthc.domain.model.food

data class SearchProductInfo(
    val id: Int,
    val ingredientList: String,
    val likes: Int,
    val nutrition: ProductNutrition,
    val title: String
)

data class ProductNutrition(
    val nutrients: List<ProductNutrient>
)

data class ProductNutrient(
    val amount: Int,
    val name: String,
    val unit: String
)