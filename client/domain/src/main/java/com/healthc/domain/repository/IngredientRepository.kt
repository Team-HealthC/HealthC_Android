package com.healthc.domain.repository

interface IngredientRepository {
    suspend fun getIngredientList(detectedObject: String): Result<List<String>>
}