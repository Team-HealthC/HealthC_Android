package com.example.healthc.data.source.detection

import com.example.healthc.data.model.local.detection.TextDetectionResponse
import com.example.healthc.data.model.remote.detection.ObjectDetectionResponse

interface DetectionDataSource {
    suspend fun getDetectedObject(image: ByteArray) : Result<ObjectDetectionResponse>

    suspend fun getDetectedKoreanText(imageUri: String): Result<TextDetectionResponse>

    suspend fun getDetectedEnglishText(imageUri: String): Result<TextDetectionResponse>
}