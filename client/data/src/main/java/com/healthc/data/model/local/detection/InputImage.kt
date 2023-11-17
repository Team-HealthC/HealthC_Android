package com.healthc.data.model.local.detection

import android.graphics.Bitmap


data class InputImage (
    val bitmap: Bitmap,
    val inputImageWidth: Float,
    val inputImageHeight: Float,
    val imageViewWidth: Float,
    val imageViewHeight: Float,
)