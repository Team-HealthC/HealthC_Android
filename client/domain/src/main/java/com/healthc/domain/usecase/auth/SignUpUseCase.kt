package com.healthc.domain.usecase.auth

import com.healthc.domain.model.auth.UserAccount
import com.healthc.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(
        userAccount: UserAccount, name: String, allergies: List<String>
    ): Result<Unit>{
        return authRepository.signUp(userAccount, name, allergies)
    }
}