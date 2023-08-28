package com.example.healthc.presentation.profile.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.usecase.user.GetUserUseCase
import com.example.healthc.domain.usecase.user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {
    private val _editorEvent: MutableSharedFlow<EditorEvent> = MutableSharedFlow<EditorEvent>()
    val editorEvent: SharedFlow<EditorEvent> get() = _editorEvent

    private val _allergies: MutableStateFlow<List<String>> = MutableStateFlow<List<String>>(emptyList())
    val allergies : StateFlow<List<String>> get() = _allergies

    private val _name: MutableStateFlow<String> = MutableStateFlow<String>("")
    val name : StateFlow<String> get() = _name

    fun setAllergy(allergyList : List<String>){
        _allergies.value = allergyList
    }

    fun getProfile() {
        viewModelScope.launch {
            getUserUseCase()
                .onSuccess {  user ->
                    _allergies.value = user.allergies
                    _name.value = user.name
                }
                .onFailure { error ->
                    _editorEvent.emit(EditorEvent.Failure(error.message))
                }
        }
    }

    fun updateProfile(){
        viewModelScope.launch{
            updateUserUseCase(_name.value, _allergies.value)
                .onSuccess {
                    _editorEvent.emit(EditorEvent.Success)
                }
                .onFailure { error ->
                    _editorEvent.emit(EditorEvent.Failure(error.message))
                }
        }
    }


    sealed class EditorEvent {
        data class Failure(val message: String?) : EditorEvent()
        object Success : EditorEvent()
    }
}