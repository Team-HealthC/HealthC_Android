package com.example.healthc.domain.repository

import com.example.healthc.domain.model.auth.User
import com.example.healthc.domain.model.auth.UserAccount

interface AuthRepository {
    suspend fun signIn(userAccount: UserAccount) : Result<Unit>

    suspend fun signUp(userAccount: UserAccount, user: User): Result<Unit>

    suspend fun signOut(): Result<Unit>
}