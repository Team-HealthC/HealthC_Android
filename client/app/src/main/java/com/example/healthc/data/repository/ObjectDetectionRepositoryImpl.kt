package com.example.healthc.data.repository

import com.example.healthc.data.source.object_detection.ObjectDetectionDataSource
import com.example.healthc.data.source.recipe.RecipeDataSource
import com.example.healthc.domain.model.recipe.Ingredient
import com.example.healthc.domain.model.object_detection.DetectedObject
import com.example.healthc.domain.repository.ObjectDetectionRepository
import com.example.healthc.domain.utils.Resource
import javax.inject.Inject

class ObjectDetectionRepositoryImpl @Inject constructor(
    private val objectDetectionDataSource: ObjectDetectionDataSource,
    private val recipeDataSource: RecipeDataSource
): ObjectDetectionRepository{
    override suspend fun postFoodImage(encodedImage: String): Resource<DetectedObject> {
        return objectDetectionDataSource.postFoodImage(encodedImage)
    }

    override suspend fun getIngredients(dish: String): Resource<Ingredient> {
        return recipeDataSource.getIngredients(dish)
    }
}