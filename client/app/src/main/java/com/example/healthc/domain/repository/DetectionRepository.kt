package com.example.healthc.domain.repository

import com.example.healthc.domain.model.detection.ObjectDetection

interface DetectionRepository {
    suspend fun getDetectedObject(image: ByteArray) : Result<ObjectDetection>
}