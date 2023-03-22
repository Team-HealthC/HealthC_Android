package com.example.healthc.data.source.food.product

import com.example.healthc.domain.utils.Resource

interface SearchProductFactsDataSource {
    suspend fun searchProductFacts(id: Int): Resource<String>
}