package com.example.healthc.data.dto.object_detection

import com.example.healthc.domain.model.object_detection.DetectedObject

data class DetectedObjectDto(
    val status : String,
    val category: String,
    val probability: Double
){
    fun toDetectedObject() : DetectedObject = DetectedObject(
        status = status,
        category = category,
        probability = probability
    )
}