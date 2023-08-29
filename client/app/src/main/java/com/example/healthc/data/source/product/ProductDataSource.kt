package com.example.healthc.data.source.product

import com.example.healthc.data.model.remote.product.ProductItemResponse
import com.example.healthc.data.model.remote.product.ProductResponse
import com.example.healthc.data.model.remote.recipe.NutritionLabelResponse

interface ProductDataSource {
    suspend fun getProductList(query: String) : Result<List<ProductItemResponse>>

    suspend fun getProduct(id: Int) : Result<ProductResponse>

    suspend fun getNutritionLabel(id: Int): Result<NutritionLabelResponse>
}