package com.example.healthc.data.source.food.product

import com.example.healthc.domain.model.food.SearchProductInfo
import com.example.healthc.domain.utils.Resource

interface SearchProductInfoDataSource {
    suspend fun searchProductInfo(id: Int) : Resource<SearchProductInfo>
}