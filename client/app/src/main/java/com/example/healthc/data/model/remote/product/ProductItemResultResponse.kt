package com.example.healthc.data.model.remote.product

import com.example.healthc.domain.model.product.ProductItem

data class ProductItemResultResponse(
    val number: Int,
    val offset: Int,
    val products: List<ProductItemResponse>,
    val totalProducts: Int,
    val type: String
)

data class ProductItemResponse(
    val id: Int,
    val imageType: String,
    val title: String
){
    fun toDomain() : ProductItem = ProductItem(
        id = id,
        imageType = imageType,
        title = title
    )
}