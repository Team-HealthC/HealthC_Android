package com.example.healthc.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.use_case.GetProfile
import com.example.healthc.domain.utils.Resource
import com.example.healthc.presentation.profile.edit_profile.EditProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfile
): ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfo>(UserInfo())
    val userInfo : StateFlow<UserInfo> get() = _userInfo

    private val _profileUiEvent = MutableStateFlow<ProfileUiEvent>(ProfileUiEvent.Unit)
    val profileUiEvent : StateFlow<ProfileUiEvent>
        get() = _profileUiEvent

    // TODO init에 초기화 함수는 위험
    init{
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            val profileResult = getProfileUseCase()
            when(profileResult){
                is Resource.Success -> {
                    _profileUiEvent.value = ProfileUiEvent.Success(profileResult.result)
                }

                is Resource.Failure -> {
                    _profileUiEvent.value = ProfileUiEvent.Failure("사용자 정보를 가져오는데 실패하였습니다.")
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