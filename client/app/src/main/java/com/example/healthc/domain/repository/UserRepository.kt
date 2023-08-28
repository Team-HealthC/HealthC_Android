package com.example.healthc.domain.repository

import com.example.healthc.domain.model.auth.User

interface UserRepository {
    suspend fun getUser() : Result<User>

    suspend fun updateUser(user: User): Result<Unit>

    suspend fun updateUserName(userName: String) : Result<Unit>
}