package com.example.healthc.domain.model.object_detection

data class DetectedObject(
    val status : String,
    val category: String,
    val probability: Double
)