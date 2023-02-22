package com.example.healthc.domain.use_case

import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.repository.UserRepository
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class GetProfile @Inject constructor(
    private val auth : FirebaseAuth,
    private val repository : UserRepository
){
    suspend operator fun invoke(): Resource<UserInfo> =
        repository.getUserInfo(requireNotNull(auth.uid))
}