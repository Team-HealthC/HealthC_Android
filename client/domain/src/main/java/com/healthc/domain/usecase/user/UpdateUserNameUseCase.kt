package com.healthc.domain.usecase.user

import com.healthc.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userName : String): Result<Unit> {
        return userRepository.updateUserName(userName)
    }
}