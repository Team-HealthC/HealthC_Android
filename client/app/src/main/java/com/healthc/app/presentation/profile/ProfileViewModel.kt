package com.healthc.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthc.domain.model.auth.User
import com.healthc.domain.usecase.user.GetUserUseCase
import com.healthc.domain.usecase.user.UpdateUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserNameUseCase : UpdateUserNameUseCase,
): ViewModel() {

    private val _userUiState: MutableStateFlow<User> = MutableStateFlow<User>(User())
    val userUiState: StateFlow<User> get() =_userUiState

    private val _profileUiEvent = MutableSharedFlow<ProfileEvent>()
    val profileUiEvent : SharedFlow<ProfileEvent> get() = _profileUiEvent

    fun getProfile() {
        viewModelScope.launch {
            getUserUseCase()
                .onSuccess {  user ->
                    _userUiState.value = user
                }
                .onFailure { error ->
                    // TODO 권한이 없는 경우, 구현 (Firebase 만료)
                    _profileUiEvent.emit(ProfileEvent.Failure(error.message))
                }
        }
    }

    fun editName(name : String){
        viewModelScope.launch {
            updateUserNameUseCase(name)
                .onSuccess {
                    _userUiState.value = _userUiState.value.copy(name = name)
                }
                .onFailure { error ->
                    _profileUiEvent.emit(ProfileEvent.Failure(error.message))
                }
        }
    }

    sealed class ProfileEvent {
        data class Failure(val message: String?) : ProfileEvent()
    }
}