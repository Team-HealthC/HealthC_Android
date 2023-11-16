package com.healthc.data.source.detection

import android.graphics.Bitmap
import com.healthc.data.di.DefaultDispatcher
import com.healthc.data.model.local.detection.ObjectDetectionResult
import com.healthc.data.utils.PrePostProcessor
import com.healthc.domain.model.detection.getPreprocessingImage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.pytorch.IValue
import org.pytorch.Module
import org.pytorch.Tensor
import org.pytorch.torchvision.TensorImageUtils
import javax.inject.Inject

class LocalDetectionDataSourceImpl @Inject constructor(
    private val objectDetectionModule: Module,
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher
): LocalDetectionDataSource {
    override suspend fun getDetectedObject(
        bitmap: Bitmap,
        imageViewWidth: Float,
        imageViewHeight: Float,
    ): Result<List<ObjectDetectionResult>> =
        withContext(coroutineDispatcher) {
            runCatching {
                val tensor = transformBitmapToTensor(bitmap)
                val outputs = getTensorOutput(tensor)

               PrePostProcessor.outputsToNMSPredictions(
                    outputs,
                    getPreprocessingImage(
                        inputImageWidth = bitmap.width.toFloat(),
                        inputImageHeight = bitmap.height.toFloat(),
                        imageViewWidth = imageViewWidth,
                        imageViewHeight = imageViewHeight,
                    )
                ).toList()
            }
        }

    private fun transformBitmapToTensor(bitmap: Bitmap): Tensor{
        return TensorImageUtils.bitmapToFloat32Tensor(
            bitmap,
            PrePostProcessor.NO_MEAN_RGB,
            PrePostProcessor.NO_STD_RGB,
        )
    }

    private fun getTensorOutput(tensor: Tensor): FloatArray {
        val outputTuple = objectDetectionModule.forward(IValue.from(tensor)).toTuple()
        val outputTensor = outputTuple[0].toTensor()
        return outputTensor.dataAsFloatArray
    }
}