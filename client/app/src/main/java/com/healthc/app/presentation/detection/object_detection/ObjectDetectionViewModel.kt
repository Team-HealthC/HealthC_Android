package com.healthc.app.presentation.detection.object_detection

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthc.data.model.local.detection.InputImage
import com.healthc.data.model.local.detection.ObjectDetectionResult
import com.healthc.data.source.detection.LocalDetectionDataSource
import com.healthc.domain.usecase.detection.CheckAllergiesInImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObjectDetectionViewModel @Inject constructor(
    private val checkAllergiesInImageUseCase : CheckAllergiesInImageUseCase,
    private val localDetectionDataSource: LocalDetectionDataSource,
) : ViewModel() {

    private val _imageUrl : MutableStateFlow<String> = MutableStateFlow("")
    val imageUrl : StateFlow<String> get() = _imageUrl

    private val _detectedObjectUiState: MutableStateFlow<ObjectDetectionUiState> =
        MutableStateFlow(ObjectDetectionUiState.Init)
    val detectedObjectUiState: StateFlow<ObjectDetectionUiState> = _detectedObjectUiState.asStateFlow()

    private val _objectDetectionEvent = MutableSharedFlow<ObjectDetectionEvent>()
    val objectDetectionEvent : SharedFlow<ObjectDetectionEvent> get() = _objectDetectionEvent

    fun setImageUrl(imageUrl: String){
        _imageUrl.value = imageUrl
    }

    fun executeObjectDetection(
        bitmap: Bitmap,
        inputImageWidth: Float,
        inputImageHeight: Float,
        imageViewWidth: Float,
        imageViewHeight: Float,
    ) {
        viewModelScope.launch {
            localDetectionDataSource.getDetectedObject(
                InputImage(
                    bitmap = bitmap,
                    inputImageWidth = inputImageWidth,
                    inputImageHeight = inputImageHeight,
                    imageViewWidth = imageViewWidth,
                    imageViewHeight = imageViewHeight,
                )
            ).onSuccess { resultList ->
                _detectedObjectUiState.value = ObjectDetectionUiState.Success(resultList)
            }.onFailure { error ->
                _objectDetectionEvent.emit(ObjectDetectionEvent.Failure(error))
            }
        }
    }

    fun checkAllergies(detectedObject: String){
        viewModelScope.launch {
            checkAllergiesInImageUseCase(detectedObject)
                .onSuccess { result ->
                    // _objectDetectionEvent.emit(ObjectDetectionEvent.Detected(result))
                }
                .onFailure { error ->
                    _objectDetectionEvent.emit(ObjectDetectionEvent.Failure(error))
                }
        }
    }

    sealed class ObjectDetectionUiState {
        data object Init : ObjectDetectionUiState()
        data class Success(
            val objectDetectionResultList : List<ObjectDetectionResult>
        ) : ObjectDetectionUiState()
    }

    sealed class ObjectDetectionEvent {
        data class Failure(val error : Throwable) : ObjectDetectionEvent()
    }
}