package com.example.healthc.domain.model.text_detection

data class DetectTextResult (
    val successful: Boolean,
    val detected : Boolean,
    val detectedList: List<String>
){
    constructor(): this(false, false, emptyList())
}