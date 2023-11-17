package com.healthc.data.repository

import com.healthc.data.source.ingredient.IngredientDataSource
import com.healthc.domain.repository.IngredientRepository
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val ingredientDataSource: IngredientDataSource
) : IngredientRepository {
    override suspend fun getIngredientList(detectedObject: String): Result<List<String>> {
        return ingredientDataSource.getIngredientList(detectedObject)
            .mapCatching {
                it.ingredientList
            }
    }
}