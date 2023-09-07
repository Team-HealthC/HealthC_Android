package com.healthc.data.repository

import com.healthc.data.model.remote.auth.UserRequest
import com.healthc.data.source.user.UserDataSource
import com.healthc.domain.model.auth.User
import com.healthc.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository{
    override suspend fun getUser(): Result<User> {
        return userDataSource.getUser().mapCatching { response ->
            response.toDomain()
        }
    }

    override suspend fun updateUser(name: String, allegies: List<String>) : Result<Unit> {
        return userDataSource.updateUser(
            UserRequest(name, allegies)
        )
    }

    override suspend fun updateUserName(userName: String): Result<Unit> {
        return userDataSource.updateUserName(userName)
    }
}