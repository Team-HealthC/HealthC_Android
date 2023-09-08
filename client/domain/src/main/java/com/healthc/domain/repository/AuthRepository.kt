package com.healthc.domain.repository

import com.healthc.domain.model.auth.UserAccount

interface AuthRepository {
    suspend fun signIn(userAccount: UserAccount) : Result<Unit>

    suspend fun signUp(userAccount: UserAccount, name: String, allergies: List<String>): Result<Unit>

    suspend fun signOut(): Result<Unit>

    suspend fun deregister(): Result<Unit>
}