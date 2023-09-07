package com.healthc.domain.model.product

data class NutritionLabel(
    val label: ByteArray
){
    constructor() : this(byteArrayOf())
}