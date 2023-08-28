package com.example.healthc.domain.usecase.auth

import com.example.healthc.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(): Result<Unit>{
        return authRepository.signOut()
    }
}