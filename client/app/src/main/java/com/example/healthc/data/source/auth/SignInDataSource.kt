package com.example.healthc.data.source.auth

import com.example.healthc.data.dto.auth.UserDto
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface SignInDataSource {
    suspend fun signIn(userDto: UserDto) : Resource<FirebaseUser>
}