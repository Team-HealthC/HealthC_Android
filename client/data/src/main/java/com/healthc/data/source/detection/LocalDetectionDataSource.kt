package com.healthc.data.source.detection

import android.graphics.Bitmap
import com.healthc.data.model.local.detection.ObjectDetectionResult

interface LocalDetectionDataSource {
    suspend fun getDetectedObject(
        bitmap: Bitmap,
        imageViewWidth: Float,
        imageViewHeight: Float,
    ): Result<List<ObjectDetectionResult>>
}