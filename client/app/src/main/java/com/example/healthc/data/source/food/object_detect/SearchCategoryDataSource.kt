package com.example.healthc.data.source.food.object_detect

import com.example.healthc.domain.model.food.SearchFoodCategory
import com.example.healthc.domain.utils.Resource

interface SearchCategoryDataSource {
    suspend fun searchFoodCategory(encodedImage: String) : Resource<SearchFoodCategory>
}