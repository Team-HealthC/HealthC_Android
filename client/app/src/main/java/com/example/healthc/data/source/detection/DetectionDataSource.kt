package com.example.healthc.data.source.detection

import com.example.healthc.data.model.remote.detection.ObjectDetectionResponse

interface DetectionDataSource {
    suspend fun getDetectedObject(image: ByteArray) : Result<ObjectDetectionResponse>
}