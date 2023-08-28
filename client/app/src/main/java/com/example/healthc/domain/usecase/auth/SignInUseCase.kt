package com.example.healthc.domain.usecase.auth

import com.example.healthc.domain.model.auth.UserAccount
import com.example.healthc.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(userAccount: UserAccount): Result<Unit>{
        return authRepository.signIn(userAccount)
    }
}