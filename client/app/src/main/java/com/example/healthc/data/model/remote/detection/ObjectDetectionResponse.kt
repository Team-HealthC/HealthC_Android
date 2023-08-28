package com.example.healthc.data.model.remote.detection

import com.example.healthc.domain.model.detection.ObjectDetection


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