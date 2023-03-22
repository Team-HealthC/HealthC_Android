package com.example.healthc.domain.model.food

data class SearchProductInfo(
    val id: Int,
    val ingredientList: String,
    val likes: Int,
    val nutrition: ProductNutrition,
    val title: String
){
    constructor() : this(0, "", 0, ProductNutrition(emptyList()),"")
}

data class ProductNutrition(
    val nutrients: List<String>
)