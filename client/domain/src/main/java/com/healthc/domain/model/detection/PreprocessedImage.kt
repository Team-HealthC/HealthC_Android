package com.healthc.domain.model.detection

data class PreprocessedImage (
    val inputImageScaleX: Float,
    val inputImageScaleY: Float,
    val imageViewScaleX: Float,
    val imageViewScaleY: Float,
    val imageStartX: Float,
    val imageStartY: Float,
) {
    companion object {
        const val DEFAULT_IMAGE_WIDTH = 640f
        const val DEFAULT_IMAGE_HEIGHT = 640f
    }
}

fun getPreprocessingImage(
    inputImageWidth: Float,
    inputImageHeight: Float,
    imageViewWidth: Float,
    imageViewHeight: Float,
): PreprocessedImage {
    // 이미지뷰(mImageView)의 크기와 비트맵(mBitmap)의 크기 간에 적절한 스케일링을 계산
    val scaleX = if (inputImageWidth > inputImageHeight) {
        imageViewWidth / inputImageWidth
    } else {
        imageViewHeight / inputImageHeight
    }

    val scaleY = if (inputImageHeight > inputImageWidth) {
        imageViewHeight / inputImageHeight
    } else {
        imageViewWidth / inputImageWidth
    }

    return PreprocessedImage(
        inputImageScaleX = inputImageWidth / PreprocessedImage.DEFAULT_IMAGE_WIDTH,
        inputImageScaleY = inputImageHeight / PreprocessedImage.DEFAULT_IMAGE_HEIGHT,
        imageViewScaleX = scaleX,
        imageViewScaleY = scaleY,
        imageStartX = (imageViewWidth - scaleX * inputImageWidth) / 2,
        imageStartY = (imageViewHeight - scaleY * inputImageHeight) / 2,
    )
}