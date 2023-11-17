package com.healthc.data.source.ingredient

import com.healthc.data.model.remote.ingredient.FirebaseIngredientResponse

interface IngredientDataSource {
    suspend fun getIngredientList(detectedObject: String): Result<FirebaseIngredientResponse>
}