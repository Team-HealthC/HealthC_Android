package com.example.healthc.domain.model.food

data class SearchProductId(
    val number: Int,
    val offset: Int,
    val products: List<ProductId>,
    val totalProducts: Int,
    val type: String
)

data class ProductId(
    val id: Int,
    val imageType: String,
    val title: String
)