package com.example.healthc.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.use_case.GetProfile
import com.example.healthc.domain.use_case.UpdateUserName
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfile,
    private val updateUserNameUseCase : UpdateUserName
): ViewModel() {

    val userInfo = MutableLiveData<UserInfo>(UserInfo())

    private val _profileUiEvent = MutableStateFlow<ProfileUiEvent>(ProfileUiEvent.Unit)
    val profileUiEvent : StateFlow<ProfileUiEvent>
        get() = _profileUiEvent

    fun getProfile() {
        viewModelScope.launch {
            val profileResult = getProfileUseCase()
            when(profileResult){
                is Resource.Success -> {
                    userInfo.value = requireNotNull(profileResult.result)
                    _profileUiEvent.value = ProfileUiEvent.Success(profileResult.result)
                }

                is Resource.Failure -> {
                    _profileUiEvent.value = ProfileUiEvent.Failure("사용자 정보를 가져오는데 실패하였습니다.")
                }

                is Resource.Loading -> { }
            }
        }
    }

    fun editName(name : String){
        viewModelScope.launch {
            val updateResult = updateUserNameUseCase(name)
            when(updateResult){
                is Resource.Success -> {
                    userInfo.value = requireNotNull(userInfo.value).copy(name = name)
                }

                is Resource.Failure -> {
                    _profileUiEvent.value = ProfileUiEvent.Failure("정보 업데이트에 실패하였습니다.")
                }

                is Resource.Loading -> { }
            }
        }
    }

    sealed class ProfileUiEvent {
        data class Failure(val message: String?) : ProfileUiEvent()
        data class Success(val userInfo: UserInfo) : ProfileUiEvent()
        object Unit : ProfileUiEvent()
    }
}