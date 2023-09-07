package com.healthc.data.model.remote.recipe

import com.healthc.domain.model.product.NutritionLabel
import com.squareup.moshi.Json

data class NutritionLabelResponse(
    @Json(name = "nutritionLabel") val label: ByteArray
){
    fun toDomain() = NutritionLabel(
        label = label
    )
}