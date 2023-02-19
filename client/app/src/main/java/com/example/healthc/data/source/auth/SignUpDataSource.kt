package com.example.healthc.data.source.auth

import com.example.healthc.data.entity.auth.UserEntity
import com.example.healthc.data.entity.auth.UserInfoEntity
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface SignUpDataSource {
    suspend fun signUp(userEntity: UserEntity, userInfoEntity: UserInfoEntity) : Resource<FirebaseUser>
}