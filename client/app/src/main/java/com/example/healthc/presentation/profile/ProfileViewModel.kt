package com.example.healthc.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.use_case.GetProfile
import com.example.healthc.domain.utils.Resource
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

    // TODO init에 초기화 함수는 위험
    init{
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            val profileResult = getProfileUseCase()
            when(profileResult){
                is Resource.Success -> {
                    _userInfo.value = profileResult.result
                }

                is Resource.Failure -> {

                }

                is Resource.Loading -> { }
            }
        }
    }
}