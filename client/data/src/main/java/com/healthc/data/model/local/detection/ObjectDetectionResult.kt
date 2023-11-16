package com.healthc.data.model.local.detection

import android.graphics.Rect

data class ObjectDetectionResult (
    val classIndex: Int,
    val score: Float,
    val rect: Rect
) {
    fun compareTo(other: ObjectDetectionResult): Int {
        return this.score.compareTo(other.score)
    }
}