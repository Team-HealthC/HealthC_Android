package com.example.healthc.presentation.detection.text_detection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.auth.Allergy
import com.example.healthc.domain.usecase.detection.CheckAllergiesInEnglishTextUseCase
import com.example.healthc.domain.usecase.detection.CheckAllergiesInKoreanTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TextDetectionViewModel @Inject constructor(
    private val checkAllergiesInKoreanTextUseCase: CheckAllergiesInKoreanTextUseCase,
    private val checkAllergiesInEnglishTextUseCase: CheckAllergiesInEnglishTextUseCase
): ViewModel() {
    private val _textDetectionUiEvent: MutableSharedFlow<TextDetectionEvent> = MutableSharedFlow()
    val textDetectionUiEvent: SharedFlow<TextDetectionEvent> get() = _textDetectionUiEvent

    private val _imageUrl: MutableStateFlow<String> = MutableStateFlow("")
    val imageUrl: StateFlow<String> get() = _imageUrl

    fun setImageUrl(imageUrl : String){
        _imageUrl.value = imageUrl
    }

    fun checkAllergiesInKoreanText() {
        viewModelScope.launch {
            checkAllergiesInKoreanTextUseCase(_imageUrl.value)
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

    fun checkAllergiesInEnglishText() {
        viewModelScope.launch {
            checkAllergiesInEnglishTextUseCase(_imageUrl.value)
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

    sealed class TextDetectionEvent {
        data class Detected(val detectedList: List<Allergy>) : TextDetectionEvent()
        object NotDetected: TextDetectionEvent()
        data class Failure(val message : String?) : TextDetectionEvent()
    }
}