package com.example.healthc.domain.model.detection

data class ObjectDetection(
    val status : String,
    val category: String,
    val probability: Double
)