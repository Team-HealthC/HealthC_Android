package com.example.healthc.data.source.object_detection

import com.example.healthc.domain.model.object_detection.DetectedObject
import com.example.healthc.domain.utils.Resource

interface ObjectDetectionDataSource {
    suspend fun postFoodImage(encodedImage: String) : Resource<DetectedObject>
}