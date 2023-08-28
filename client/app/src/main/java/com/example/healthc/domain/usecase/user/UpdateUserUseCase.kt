package com.example.healthc.domain.usecase.user

import com.example.healthc.domain.model.auth.User
import com.example.healthc.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userName: String, allergies: List<String>): Result<Unit> {
        return userRepository.updateUser(
            User(userName, allergies)
        )
    }
}