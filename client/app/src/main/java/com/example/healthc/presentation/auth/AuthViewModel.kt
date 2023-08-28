package com.example.healthc.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.auth.User
import com.example.healthc.domain.model.auth.UserAccount
import com.example.healthc.domain.usecase.auth.SignInUseCase
import com.example.healthc.domain.usecase.auth.SignUpUseCase
import com.example.healthc.domain.usecase.validation.ValidateEmailUseCase
import com.example.healthc.domain.usecase.validation.ValidateNameUseCase
import com.example.healthc.domain.usecase.validation.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _validationEvent = MutableSharedFlow<AuthEvent>()
    val validationEvent: SharedFlow<AuthEvent> get() = _validationEvent

    private val _signUpEvent = MutableSharedFlow<AuthEvent>()
    val signUpEvent: SharedFlow<AuthEvent> get() = _signUpEvent

    private val _signInEvent = MutableSharedFlow<AuthEvent>()
    val signInEvent: SharedFlow<AuthEvent> get() = _signInEvent

    // User
    val email: MutableStateFlow<String> = MutableStateFlow<String>("")
    val password: MutableStateFlow<String> = MutableStateFlow<String>("")
    val name: MutableStateFlow<String> = MutableStateFlow<String>("")
    val allergies: MutableStateFlow<List<String>> = MutableStateFlow<List<String>>(emptyList())

    fun validateEmail(){
        viewModelScope.launch {
            val validateEmailUseCase = ValidateEmailUseCase()
            val result = validateEmailUseCase(email.value)
            if(result.successful){
                _validationEvent.emit(AuthEvent.Success)
            }else{
                _validationEvent.emit(AuthEvent.Failure(result.errorMessage))
            }
        }
    }

    fun validatePassword(){
        viewModelScope.launch {
            val validatePasswordUseCase = ValidatePasswordUseCase()
            val result = validatePasswordUseCase(password.value)
            if(result.successful){
                _validationEvent.emit(AuthEvent.Success)
            }else{
                _validationEvent.emit(AuthEvent.Failure(result.errorMessage))
            }
        }
    }

    fun validateName(){
        viewModelScope.launch {
            val validateNameUseCase = ValidateNameUseCase()
            val result = validateNameUseCase(name.value)
            if(result.successful){
                _validationEvent.emit(AuthEvent.Success)
            }else{
                _validationEvent.emit(AuthEvent.Failure(result.errorMessage))
            }
        }
    }

    fun setAllergy(allergyList : List<String>){
        allergies.value = allergyList
    }

    fun signInUser(){
        viewModelScope.launch {
            signInUseCase(
                UserAccount(email.value, password.value)
            ).onSuccess {
                _signInEvent.emit(AuthEvent.Success)
            }.onFailure { error ->
                _signInEvent.emit(AuthEvent.Failure(error.message))
            }
        }
    }

    fun signUpUser(){
        viewModelScope.launch {
            signUpUseCase(
                userAccount = UserAccount(email.value, password.value),
                user = User(name.value, allergies.value)
            ).onSuccess {
                _signUpEvent.emit(AuthEvent.Success)
            }.onFailure { error ->
                _signUpEvent.emit(AuthEvent.Failure(error.message))
            }
        }
    }

    sealed class AuthEvent {
        data class Failure(val message: String?) : AuthEvent()
        object Success : AuthEvent()
    }
}