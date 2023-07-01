package com.example.healthc.domain.model.product

data class NutritionLabel(
    val nutritionLabel: ByteArray
){
    constructor() : this(byteArrayOf())
}