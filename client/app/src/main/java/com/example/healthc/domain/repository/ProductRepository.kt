package com.example.healthc.domain.repository

import com.example.healthc.domain.model.product.NutritionLabel
import com.example.healthc.domain.model.product.Product
import com.example.healthc.domain.model.product.ProductId
import com.example.healthc.domain.utils.Resource

interface ProductRepository {
    suspend fun getProductIds(query: String) : Resource<ProductId>

    suspend fun getProduct(id: Int) : Resource<Product>

    suspend fun getNutritionLabel(id : Int) : Resource<NutritionLabel>

}