package com.healthc.domain.repository

import com.healthc.domain.model.auth.User

interface UserRepository {
    suspend fun getUser() : Result<User>

    suspend fun updateUser(name: String, allergies: List<String>): Result<Unit>

    suspend fun updateUserName(userName: String) : Result<Unit>
}