package com.example.healthc.data.repository

import com.example.healthc.data.source.detection.DetectionDataSource
import com.example.healthc.domain.model.detection.ObjectDetection
import com.example.healthc.domain.model.detection.TextDetection
import com.example.healthc.domain.repository.DetectionRepository
import javax.inject.Inject

class DetectionRepositoryImpl @Inject constructor(
    private val detectionDataSource: DetectionDataSource
): DetectionRepository{
    override suspend fun getDetectedObject(image: ByteArray): Result<ObjectDetection> {
        return detectionDataSource.getDetectedObject(image).mapCatching { label ->
            label.toDomain()
        }
    }

    override suspend fun getDetectedKoreanText(imageUri: String): Result<TextDetection> {
        return detectionDataSource.getDetectedKoreanText(imageUri).mapCatching { text ->
            text.toDomain()
        }
    }

    override suspend fun getDetectedEnglishText(imageUri: String): Result<TextDetection> {
        return detectionDataSource.getDetectedEnglishText(imageUri).mapCatching { text ->
            text.toDomain()
        }
    }
}