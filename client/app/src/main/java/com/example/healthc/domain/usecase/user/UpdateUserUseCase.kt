package com.example.healthc.domain.usecase.user

import com.example.healthc.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(name: String, allergies: List<String>): Result<Unit> {
        return userRepository.updateUser(name, allergies)
    }
}