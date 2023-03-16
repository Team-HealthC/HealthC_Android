package com.example.healthc.data.source.food.product

import com.example.healthc.domain.model.food.SearchProductId
import com.example.healthc.domain.utils.Resource

interface SearchProductIdDataSource {
    suspend fun searchProduct(query: String) : Resource<SearchProductId>
}
