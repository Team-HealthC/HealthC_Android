package com.example.healthc.domain.repository

import com.example.healthc.domain.model.detection.ObjectDetection
import com.example.healthc.domain.model.detection.TextDetection

interface DetectionRepository {
    suspend fun getDetectedObject(image: ByteArray) : Result<ObjectDetection>

    suspend fun getDetectedKoreanText(imageUri: String): Result<TextDetection>

    suspend fun getDetectedEnglishText(imageUri: String): Result<TextDetection>
}