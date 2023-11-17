package com.healthc.data.source.detection

import android.graphics.Bitmap
import com.healthc.data.model.local.detection.InputImage
import com.healthc.data.model.local.detection.ObjectDetectionResult

interface LocalDetectionDataSource {
    suspend fun getDetectedObject(inputImage: InputImage): Result<List<ObjectDetectionResult>>
}