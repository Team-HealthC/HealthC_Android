package com.example.healthc.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.repository.AuthRepository
import com.example.healthc.domain.model.auth.User
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.use_case.ValidateEmail
import com.example.healthc.domain.use_case.ValidateName
import com.example.healthc.domain.use_case.ValidatePassword
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signUpUiEvent = MutableStateFlow<SignUpUiEvent>(SignUpUiEvent.Unit)
    val signUpUiEvent : StateFlow<SignUpUiEvent>
        get() = _signUpUiEvent

    private val _signUpEvent = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signUpEvent : StateFlow<Resource<FirebaseUser>?>
        get() = _signUpEvent

    private val _signInEvent = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signInEvent : StateFlow<Resource<FirebaseUser>?>
        get() = _signInEvent

    // 사용자 input
    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val name = MutableLiveData<String>("")
    val allergys = MutableLiveData<List<String>>(emptyList())

    // 유효성 검사
    private val validateEmailUseCase by lazy { ValidateEmail() }
    private val validatePasswordUseCase by lazy { ValidatePassword() }
    private val validateNameUseCase by lazy { ValidateName() }

    fun validateEmail(){
        val result = validateEmailUseCase(requireNotNull(email.value))
        if(result.successful){
            _signUpUiEvent.value = SignUpUiEvent.Success
        }else{
            _signUpUiEvent.value = SignUpUiEvent.Failure(result.errorMessage)
        }
    }

    fun validatePassword(){
        val result = validatePasswordUseCase(requireNotNull(password.value))
        if(result.successful){
            _signUpUiEvent.value = SignUpUiEvent.Success
        }else{
            _signUpUiEvent.value = SignUpUiEvent.Failure(result.errorMessage)
        }
    }

    fun validateName(){
        val result = validateNameUseCase(requireNotNull(name.value))
        if(result.successful){
            _signUpUiEvent.value = SignUpUiEvent.Success
        }else{
            _signUpUiEvent.value = SignUpUiEvent.Failure(result.errorMessage)
        }
    }

    fun setAllergy(allergyList : MutableList<String>){
        allergys.value = allergyList.toList()
    }

    fun signInUser(){
        viewModelScope.launch {
            _signInEvent.value = Resource.Loading
            val result = authRepository.signIn(
                User(requireNotNull(email.value), requireNotNull(password.value)),
            )
            _signInEvent.value = result
        }
    }

    fun signUpUser(){
        viewModelScope.launch {
            _signUpEvent.value = Resource.Loading
            val result = authRepository.signUp(
                User(requireNotNull(email.value), requireNotNull(password.value)),
                UserInfo(requireNotNull(name.value), emptyList(), requireNotNull(allergys.value))
            )
            _signUpEvent.value = result
        }
    }

    fun initState(){
        _signUpUiEvent.value = SignUpUiEvent.Unit
    }

    // 로그인 -> 회원가입으로 넘어갈 때
    fun initInput(){
        email.value = ""
        password.value = ""
    }

    fun signOut() {
        authRepository.signOut()
        _signInEvent.value = null
        _signUpEvent.value = null
    }

    sealed class SignUpUiEvent {
        data class Failure(val message: String?) : SignUpUiEvent()
        object Success : SignUpUiEvent()
        object Unit : SignUpUiEvent()
    }
}