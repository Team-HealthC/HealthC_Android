package com.example.healthc.presentation.profile.profile_allergy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.use_case.GetProfile
import com.example.healthc.domain.use_case.UpdateProfile
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileAllergyViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfile,
    private val getProfileUseCase: GetProfile
): ViewModel() {

    private val _profileUiEvent = MutableSharedFlow<ProfileUiEvent>()
    val profileUiEvent : SharedFlow<ProfileUiEvent> get() = _profileUiEvent

    private val _profileAllergy = MutableStateFlow<List<String>>(emptyList())
    val profileAllergy : StateFlow<List<String>> get() = _profileAllergy

    private val _profileUserName = MutableStateFlow<String>("")
    val profileUserName : StateFlow<String> get() = _profileUserName

    fun getProfile() {
        viewModelScope.launch {
            val profileResult = getProfileUseCase()
            when(profileResult){
                is Resource.Success -> {
                    val userInfo = profileResult.result
                    _profileUserName.value = userInfo.name
                    _profileAllergy.value = userInfo.allergy
                }
                is Resource.Failure -> {
                    _profileUiEvent.emit(ProfileUiEvent.Failure("사용자 정보를 가져오는데 실패하였습니다."))
                }
                is Resource.Loading -> { }
            }
        }
    }

    fun setAllergy(allergyList : List<String>){
        _profileAllergy.value = allergyList
    }

    fun updateProfile(){
        viewModelScope.launch{
            val profileEditResult = updateProfileUseCase(
                UserInfo(
                    _profileUserName.value,
                    _profileAllergy.value
                )
            )
            when(profileEditResult){
                is Resource.Success -> {
                    _profileUiEvent.emit(ProfileUiEvent.Success)
                }
                is Resource.Failure -> {
                    _profileUiEvent.emit(ProfileUiEvent.Failure("사용자 정보를 업데이트 하는데 실패하였습니다."))
                }
                is Resource.Loading -> { }
            }
        }
    }

    sealed class ProfileUiEvent {
        data class Failure(val message: String?) : ProfileUiEvent()
        object Success : ProfileUiEvent()
    }
}