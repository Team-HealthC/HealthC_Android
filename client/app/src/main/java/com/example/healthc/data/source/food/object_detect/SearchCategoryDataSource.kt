package com.example.healthc.data.source.food.object_detect

import com.example.healthc.domain.model.food.SearchFoodCategory
import com.example.healthc.domain.utils.Resource
import java.io.File

interface SearchCategoryDataSource {
    suspend fun searchFoodCategory(fileUrl: String, file : File) : Resource<SearchFoodCategory>
}