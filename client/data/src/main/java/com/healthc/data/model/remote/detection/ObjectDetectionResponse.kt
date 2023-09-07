package com.healthc.data.model.remote.detection

import com.healthc.domain.model.detection.ObjectDetection

data class ObjectDetectionResponse(
    val status : String,
    val category: String,
    val probability: Double
){
    fun toDomain(): ObjectDetection = ObjectDetection(
        status = status,
        category = category,
        probability = probability
    )
}