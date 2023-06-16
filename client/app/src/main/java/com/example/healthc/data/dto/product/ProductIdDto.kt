package com.example.healthc.data.dto.product

import com.example.healthc.domain.model.product.ProductId
import com.example.healthc.domain.model.product.ProductIdInfo

data class ProductIdDto(
    val number: Int,
    val offset: Int,
    val products: List<ProductIdInfoDto>,
    val totalProducts: Int,
    val type: String
){
    fun toProductId() : ProductId = ProductId(
        number = number,
        offset = offset,
        products = products.map{ it.toProductIdInfo() },
        totalProducts = totalProducts,
        type = type
    )
}

data class ProductIdInfoDto(
    val id: Int,
    val imageType: String,
    val title: String
){
    fun toProductIdInfo() : ProductIdInfo = ProductIdInfo(
        id = id,
        imageType = imageType,
        title = title
    )
}