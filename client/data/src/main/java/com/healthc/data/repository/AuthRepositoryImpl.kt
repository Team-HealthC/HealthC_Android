package com.healthc.data.repository

import com.healthc.data.source.auth.AuthDataSource
import com.healthc.domain.repository.AuthRepository
import com.healthc.domain.model.auth.UserAccount
import com.healthc.data.model.remote.auth.UserAccountRequest
import com.healthc.data.model.remote.auth.UserRequest
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

    override suspend fun deregister(): Result<Unit> {
        return authDataSource.deregister()
    }
}