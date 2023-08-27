package com.example.healthc.data.model.remote.recipe

import com.example.healthc.domain.model.product.NutritionLabel

data class NutritionLabelResponse(
    val label: ByteArray
){
    fun toDomain() = NutritionLabel(
        label = label
    )
}