package com.example.healthc.data.source.auth

import com.example.healthc.data.model.remote.auth.UserAccountRequest
import com.example.healthc.data.model.remote.auth.UserRequest

interface AuthDataSource {
    suspend fun signIn(userAccountRequest: UserAccountRequest) : Result<Unit>

    suspend fun signUp(userAccountRequest: UserAccountRequest, userRequest: UserRequest) : Result<Unit>

    suspend fun signOut(): Result<Unit>
}