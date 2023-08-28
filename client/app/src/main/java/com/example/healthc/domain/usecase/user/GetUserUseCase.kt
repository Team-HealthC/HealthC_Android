package com.example.healthc.domain.usecase.user

import com.example.healthc.domain.model.auth.User
import com.example.healthc.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository : UserRepository
){
    suspend operator fun invoke(): Result<User>{
        return userRepository.getUser()
    }
}