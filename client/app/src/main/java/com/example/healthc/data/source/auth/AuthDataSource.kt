package com.example.healthc.data.source.auth

import com.example.healthc.data.dto.auth.UserDto
import com.example.healthc.data.dto.auth.UserInfoDto
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthDataSource {
    suspend fun signIn(userDto: UserDto) : Resource<FirebaseUser>

    suspend fun signUp(userDto: UserDto, userInfoDto: UserInfoDto) : Resource<FirebaseUser>
}