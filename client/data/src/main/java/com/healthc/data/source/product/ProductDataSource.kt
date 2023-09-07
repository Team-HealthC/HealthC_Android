package com.healthc.data.source.product

import com.healthc.data.model.remote.product.ProductItemResponse
import com.healthc.data.model.remote.product.ProductResponse
import com.healthc.data.model.remote.recipe.NutritionLabelResponse

interface ProductDataSource {
    suspend fun getProductList(query: String) : Result<List<ProductItemResponse>>

    suspend fun getProduct(id: Int) : Result<ProductResponse>

    suspend fun getNutritionLabel(id: Int): Result<NutritionLabelResponse>
}