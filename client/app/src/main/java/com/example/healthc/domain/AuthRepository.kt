package com.example.healthc.domain

import com.example.healthc.domain.model.auth.User
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun signIn(user: User) : Resource<FirebaseUser>

    suspend fun signUp(user: User, userInfo: UserInfo): Resource<FirebaseUser>

    fun logout()
}