package com.example.healthc.data.repository

import com.example.healthc.data.model.remote.auth.UserAccountRequest
import com.example.healthc.data.model.remote.auth.UserRequest
import com.example.healthc.data.source.auth.AuthDataSource
import com.example.healthc.domain.model.auth.User
import com.example.healthc.domain.repository.AuthRepository
import com.example.healthc.domain.model.auth.UserAccount
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun signIn(userAccount: UserAccount): Result<Unit> {
        return authDataSource.signIn(
            userAccountRequest = UserAccountRequest(userAccount.email, userAccount.password)
        )
    }

    override suspend fun signUp(
        userAccount: UserAccount, name: String, allergies: List<String>
    ): Result<Unit> {
        return authDataSource.signUp(
            userAccountRequest = UserAccountRequest(userAccount.email, userAccount.password),
            userRequest = UserRequest(name, allergies)
        )
    }

    override suspend fun signOut(): Result<Unit> {
        return authDataSource.signOut()
    }
}