package com.healthc.app.presentation.detection.object_detection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthc.domain.model.auth.Allergy
import com.healthc.domain.model.detection.ObjectDetection
import com.healthc.domain.usecase.detection.CheckAllergiesInImageUseCase
import com.healthc.domain.usecase.detection.GetDetectedObjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObjectDetectionViewModel @Inject constructor(
    private val checkAllergiesInImageUseCase : CheckAllergiesInImageUseCase,
    private val getDetectObjectUseCase: GetDetectedObjectUseCase
) : ViewModel() {

    private val _imageUrl : MutableStateFlow<String> = MutableStateFlow("")
    val imageUrl : StateFlow<String> get() = _imageUrl

    private val _objectDetectionEvent = MutableSharedFlow<ObjectDetectionEvent>()
    val objectDetectionEvent : SharedFlow<ObjectDetectionEvent> get() = _objectDetectionEvent

    fun setImageUrl(imageUrl: String){
        _imageUrl.value = imageUrl
    }

    fun postImage(image: ByteArray) {
        viewModelScope.launch {
            getDetectObjectUseCase(image)
                .onSuccess {  result ->
                    _objectDetectionEvent.emit(ObjectDetectionEvent.Success(result))
                }
                .onFailure { error ->
                    _objectDetectionEvent.emit(ObjectDetectionEvent.Failure(error.message))
                }
        }
    }

    fun checkAllergies(detectedObject: String){
        viewModelScope.launch {
            checkAllergiesInImageUseCase(detectedObject)
                .onSuccess { result ->
                    _objectDetectionEvent.emit(ObjectDetectionEvent.Detected(result))
                }
                .onFailure { error ->
                    _objectDetectionEvent.emit(ObjectDetectionEvent.Failure(error.message))
                }
        }
    }

    sealed class ObjectDetectionEvent {
        data class Failure(val message : String?) : ObjectDetectionEvent()
        data class Success(val category: ObjectDetection) : ObjectDetectionEvent()
        data class Detected(val detectedList : List<Allergy>) : ObjectDetectionEvent()
    }
}