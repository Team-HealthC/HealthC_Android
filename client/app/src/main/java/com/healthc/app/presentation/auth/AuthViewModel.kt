package com.healthc.app.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthc.domain.model.auth.UserAccount
import com.healthc.domain.usecase.auth.SignInUseCase
import com.healthc.domain.usecase.auth.SignUpUseCase
import com.healthc.domain.usecase.validation.ValidateEmailUseCase
import com.healthc.domain.usecase.validation.ValidateNameUseCase
import com.healthc.domain.usecase.validation.ValidatePasswordUseCase
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

    private val _validationEvent = MutableSharedFlow<ValidateEvent>()
    val validationEvent: SharedFlow<ValidateEvent> get() = _validationEvent

    private val _signUpEvent = MutableSharedFlow<AuthEvent>()
    val signUpEvent: SharedFlow<AuthEvent> get() = _signUpEvent

    private val _signInEvent = MutableSharedFlow<AuthEvent>()
    val signInEvent: SharedFlow<AuthEvent> get() = _signInEvent

    // User
    val email: MutableStateFlow<String> = MutableStateFlow<String>("")
    val password: MutableStateFlow<String> = MutableStateFlow<String>("")
    val name: MutableStateFlow<String> = MutableStateFlow<String>("")
    private val allergies: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    fun validateInput() {
        if(validateName() && validateEmail() && validatePassword()) {
            viewModelScope.launch {
                _validationEvent.emit(ValidateEvent.Success)
            }
        }
    }

    private fun validateEmail() : Boolean {
        val validateEmailUseCase = ValidateEmailUseCase()
        val result = validateEmailUseCase(email.value)
        if (result.successful.not()) {
            viewModelScope.launch {
                _validationEvent.emit(ValidateEvent.InvalidEmail(result.errorMessage))
            }
        }
        return result.successful
    }

    private fun validatePassword() : Boolean{
        val validatePasswordUseCase = ValidatePasswordUseCase()
        val result = validatePasswordUseCase(password.value)
        if (result.successful.not()) {
            viewModelScope.launch {
                _validationEvent.emit(ValidateEvent.InvalidPassword(result.errorMessage))
            }
        }
        return result.successful
    }

    private fun validateName() : Boolean{
        val validateNameUseCase = ValidateNameUseCase()
        val result = validateNameUseCase(name.value)
        if (result.successful.not()) {
            viewModelScope.launch {
                _validationEvent.emit(ValidateEvent.InvalidName(result.errorMessage))
            }
        }
        return result.successful
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
                name = name.value,
                allergies = allergies.value
            ).onSuccess {
                _signUpEvent.emit(AuthEvent.Success)
            }.onFailure { error ->
                _signUpEvent.emit(AuthEvent.Failure(error.message))
            }
        }
    }

    fun clearUserInput(){
        email.value = TEXT_EMPTY
        password.value = TEXT_EMPTY
    }

    sealed class ValidateEvent {
        data class InvalidName(val message: String?) : ValidateEvent()
        data class InvalidEmail(val message: String?) : ValidateEvent()
        data class InvalidPassword(val message: String?) : ValidateEvent()
        data object Success : ValidateEvent()
    }

    sealed class AuthEvent {
        data class Failure(val message: String?): AuthEvent()
        data object Success : AuthEvent()
    }

    companion object{
        const val TEXT_EMPTY = ""
    }
}