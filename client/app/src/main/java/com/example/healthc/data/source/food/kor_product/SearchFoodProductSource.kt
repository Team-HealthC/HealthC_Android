package com.example.healthc.data.source.food.kor_product

import com.example.healthc.domain.model.food.SearchFoodProduct
import com.example.healthc.domain.utils.Resource

interface SearchFoodProductSource {
    suspend fun searchProduct(category: String): Resource<SearchFoodProduct>
}