package com.example.healthc.presentation.detection.text_detection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.usecase.detection.CheckAllergiesInTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TextDetectionViewModel @Inject constructor(
    private val checkAllergiesInTextUseCase : CheckAllergiesInTextUseCase
): ViewModel() {
    private val _textDetectionUiEvent: MutableSharedFlow<TextDetectionEvent> = MutableSharedFlow()
    val textDetectionUiEvent: SharedFlow<TextDetectionEvent> get() = _textDetectionUiEvent

    private val _imageUrl: MutableStateFlow<String> = MutableStateFlow("")
    val imageUrl: StateFlow<String> get() = _imageUrl

    fun setImageUrl(imageUrl : String){
        _imageUrl.value = imageUrl
    }

    fun detectImage(recognizedText: String) {
        viewModelScope.launch {
            checkAllergiesInTextUseCase(recognizedText, isEnglish = false)
                .onSuccess { list ->
                    if(list.isNotEmpty())
                        _textDetectionUiEvent.emit(TextDetectionEvent.Detected(list))
                    else
                        _textDetectionUiEvent.emit(TextDetectionEvent.NotDetected)
                }
                .onFailure { error ->
                    _textDetectionUiEvent.emit(TextDetectionEvent.Failure(error.message))
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _imageUrl.value= ""
    }

    sealed class TextDetectionEvent {
        data class Detected(val detectedList: List<String>) : TextDetectionEvent()
        object NotDetected: TextDetectionEvent()
        data class Failure(val message : String?) : TextDetectionEvent()
    }
}