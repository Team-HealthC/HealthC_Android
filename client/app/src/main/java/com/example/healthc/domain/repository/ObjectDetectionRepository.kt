package com.example.healthc.domain.repository

import com.example.healthc.domain.model.recipe.Ingredient
import com.example.healthc.domain.model.object_detection.DetectedObject
import com.example.healthc.domain.utils.Resource

interface ObjectDetectionRepository {
    // 음식 이미지 전송
    suspend fun postFoodImage(encodedImage: String) : Resource<DetectedObject>

    // 음식 레시피에 포함된 재료 검색
    suspend fun getIngredients(dish : String): Resource<Ingredient>
}