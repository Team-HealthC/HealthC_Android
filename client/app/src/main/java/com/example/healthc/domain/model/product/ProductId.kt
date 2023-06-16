package com.example.healthc.domain.model.product

data class ProductId(
    val number: Int,
    val offset: Int,
    val products: List<ProductIdInfo>,
    val totalProducts: Int,
    val type: String
)

data class ProductIdInfo(
    val id: Int,
    val imageType: String,
    val title: String
)