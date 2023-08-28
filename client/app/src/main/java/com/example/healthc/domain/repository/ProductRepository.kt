package com.example.healthc.domain.repository

import com.example.healthc.domain.model.product.NutritionLabel
import com.example.healthc.domain.model.product.Product
import com.example.healthc.domain.model.product.ProductItem

interface ProductRepository {
    suspend fun getProductList(query: String) : Result<List<ProductItem>>

    suspend fun getProduct(id: Int) : Result<Product>

    suspend fun getNutritionLabel(id : Int) : Result<NutritionLabel>

}