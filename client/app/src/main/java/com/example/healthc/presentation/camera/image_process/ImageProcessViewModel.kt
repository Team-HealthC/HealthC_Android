package com.example.healthc.presentation.camera.image_process

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.use_case.DetectText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageProcessViewModel @Inject constructor(
    private val detectTextUseCase : DetectText
): ViewModel() {

    // 사진 저장 URL
    private val _imageUrl = MutableStateFlow("") // TODO Default image url 세팅
    val imageUrl : StateFlow<String>
        get() = _imageUrl

    private val _imageProcessUiEvent = MutableStateFlow<ImageProcessEvent>(ImageProcessEvent.Unit)
    val imageProcessEvent : StateFlow<ImageProcessEvent>
        get() = _imageProcessUiEvent

    fun setImageUrl(imageUrl : String){
        _imageUrl.value = imageUrl
    }

    fun detectImage(recognizedText: String) {
        viewModelScope.launch {
            val result = detectTextUseCase(recognizedText, isEnglish = false)
            if(result.successful){
                if(result.detected) {
                    _imageProcessUiEvent.value = ImageProcessEvent.Detected(result.detectedList)
                }else{
                    _imageProcessUiEvent.value = ImageProcessEvent.Success
                }
            }
            else{
                _imageProcessUiEvent.value = ImageProcessEvent.Failure("회원 정보를 가져오는데 실패하였습니다.")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _imageUrl.value= ""
    }

    sealed class ImageProcessEvent {
        data class Detected(val detectedList: List<String>) : ImageProcessEvent()
        data class Failure(val message : String) : ImageProcessEvent()
        object Success : ImageProcessEvent()
        object Unit : ImageProcessEvent()
    }
}