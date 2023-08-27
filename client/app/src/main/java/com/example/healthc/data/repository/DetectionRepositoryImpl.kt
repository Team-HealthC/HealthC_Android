package com.example.healthc.data.repository

import com.example.healthc.data.source.detection.DetectionDataSource
import com.example.healthc.domain.model.detection.ObjectDetection
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
}