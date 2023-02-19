package com.example.healthc.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository : UserRepository,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    fun getUserInfo() {
        viewModelScope.launch {
            val result = repository.getUserInfo(
                requireNotNull(firebaseAuth.uid)
            )
            Timber.d(result.toString())
        }
    }
}