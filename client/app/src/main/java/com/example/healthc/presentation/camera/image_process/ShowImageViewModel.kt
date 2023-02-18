package com.example.healthc.presentation.camera.image_process

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class ShowImageViewModel: ViewModel() {

    // 사진 저장 URL
    private val _imageUrl = MutableStateFlow("") // TODO Default image url 세팅
    val imageUrl : StateFlow<String>
        get() = _imageUrl

    fun setImageUrl(imageUrl : String){
        _imageUrl.value = imageUrl
    }

    fun onClickSavedButton(){
        Timber.d("saved Image")
    }

    override fun onCleared() {
        super.onCleared()
        _imageUrl.value= ""
    }

}