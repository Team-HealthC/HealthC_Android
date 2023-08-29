package com.example.healthc.domain.repository

import com.example.healthc.domain.model.auth.UserAccount

interface AuthRepository {
    suspend fun signIn(userAccount: UserAccount) : Result<Unit>

    suspend fun signUp(userAccount: UserAccount, name: String, allergies: List<String>): Result<Unit>

    suspend fun signOut(): Result<Unit>
}