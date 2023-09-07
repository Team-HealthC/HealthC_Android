package com.healthc.domain.usecase.user

import com.healthc.domain.model.auth.User
import com.healthc.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository : UserRepository
){
    suspend operator fun invoke(): Result<User>{
        return userRepository.getUser()
    }
}