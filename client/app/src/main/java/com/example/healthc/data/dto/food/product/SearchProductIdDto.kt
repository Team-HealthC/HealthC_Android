package com.example.healthc.data.dto.food.product

import com.example.healthc.domain.model.food.ProductId
import com.example.healthc.domain.model.food.SearchProductId

data class SearchProductIdDto(
    val number: Int,
    val offset: Int,
    val products: List<ProductIdDto>,
    val totalProducts: Int,
    val type: String
){
    fun toSearchProductId() : SearchProductId = SearchProductId(
        number = number,
        offset = offset,
        products = products.map{ it.toProductId() },
        totalProducts = totalProducts,
        type = type
    )
}

data class ProductIdDto(
    val id: Int,
    val imageType: String,
    val title: String
){
    fun toProductId() : ProductId = ProductId(
        id = id,
        imageType = imageType,
        title = title
    )
}