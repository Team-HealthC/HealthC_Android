package com.example.healthc.presentation.profile.profile_allergy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.use_case.GetProfile
import com.example.healthc.domain.use_case.UpdateProfile
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileAllergyViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfile,
    private val getProfileUseCase: GetProfile
): ViewModel() {

    private val _profileUiEvent = MutableStateFlow<ProfileUiEvent>(ProfileUiEvent.Unit)
    val profileUiEvent : StateFlow<ProfileUiEvent>
        get() = _profileUiEvent

    val userAllergy = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    private val modifiedAllergyList = MutableLiveData<List<String>>(emptyList())
    private val modifiedDiseaseList = MutableLiveData<List<String>>(emptyList())

    init{
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            val profileResult = getProfileUseCase()
            when(profileResult){
                is Resource.Success -> {
                    val userInfo = profileResult.result
                    userName.value = userInfo.name
                    userAllergy.value = userInfo.allergy.joinToString(separator = ", ")
                }
                is Resource.Failure -> {
                    _profileUiEvent.value = ProfileUiEvent.Failure("사용자 정보를 가져오는데 실패하였습니다.")
                }
                is Resource.Loading -> { }
            }
        }
    }

    fun setAllergy(allergyList : MutableList<String>){
        this.modifiedAllergyList.value = allergyList.toList()
    }

    fun updateProfile(){
        viewModelScope.launch{
            val profileEditResult = updateProfileUseCase(
                UserInfo(
                    requireNotNull(userName.value),
                    requireNotNull(modifiedAllergyList.value)
                )
            )
            when(profileEditResult){
                is Resource.Success -> {
                    _profileUiEvent.value = ProfileUiEvent.Success
                }
                is Resource.Failure -> {
                    _profileUiEvent.value = ProfileUiEvent.Failure("사용자 정보를 업데이트 하는데 실패하였습니다.")
                }
                is Resource.Loading -> { }
            }
        }
    }

    sealed class ProfileUiEvent {
        data class Failure(val message: String?) : ProfileUiEvent()
        object Success : ProfileUiEvent()
        object Unit : ProfileUiEvent()
    }
}