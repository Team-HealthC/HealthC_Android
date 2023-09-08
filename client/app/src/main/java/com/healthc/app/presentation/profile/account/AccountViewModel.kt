package com.healthc.app.presentation.profile.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthc.domain.usecase.auth.DeregisterUseCase
import com.healthc.domain.usecase.auth.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val deregisterUseCase: DeregisterUseCase,
    private val signOutUseCase: SignOutUseCase
): ViewModel() {

    private val _accountUiEvent = MutableSharedFlow<AccountUiEvent>()
    val accountUiEvent : SharedFlow<AccountUiEvent> get() = _accountUiEvent

    fun signOut(){
        viewModelScope.launch {
            signOutUseCase()
                .onSuccess {
                    _accountUiEvent.emit(AccountUiEvent.Unauthorized)
                }
                .onFailure { error ->
                    _accountUiEvent.emit(AccountUiEvent.Failure(error.message))
                }
        }
    }

    fun deregister() {
        viewModelScope.launch {
            deregisterUseCase()
                .onSuccess {
                    _accountUiEvent.emit(AccountUiEvent.Unauthorized)
                }
                .onFailure { error ->
                    _accountUiEvent.emit(AccountUiEvent.Failure(error.message))
                }
        }
    }
    
    sealed class AccountUiEvent{
        data class Failure(val message: String?) : AccountUiEvent()

        data object Unauthorized: AccountUiEvent()
    }
}