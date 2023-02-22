package com.example.healthc.data.source.auth

import com.example.healthc.data.dto.auth.UserEntity
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface SignInDataSource {
    suspend fun signIn(userEntity: UserEntity) : Resource<FirebaseUser>
}