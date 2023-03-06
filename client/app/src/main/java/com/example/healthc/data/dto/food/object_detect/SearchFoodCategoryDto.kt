package com.example.healthc.data.dto.food.object_detect

import com.example.healthc.domain.model.food.SearchFoodCategory

data class SearchFoodCategoryDto(
    val status : String,
    val category: String,
    val probability: Double
){
    fun toSearchFoodCategory() : SearchFoodCategory = SearchFoodCategory(
        status = status,
        category = category,
        probability = probability
    )
}