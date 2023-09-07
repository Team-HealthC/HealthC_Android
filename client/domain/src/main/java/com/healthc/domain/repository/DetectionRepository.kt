package com.healthc.domain.repository

import com.healthc.domain.model.detection.ObjectDetection
import com.healthc.domain.model.detection.TextDetection

interface DetectionRepository {
    suspend fun getDetectedObject(image: ByteArray) : Result<ObjectDetection>

    suspend fun getDetectedKoreanText(imageUri: String): Result<TextDetection>

    suspend fun getDetectedEnglishText(imageUri: String): Result<TextDetection>
}