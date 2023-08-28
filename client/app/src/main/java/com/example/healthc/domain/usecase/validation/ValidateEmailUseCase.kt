package com.example.healthc.domain.usecase.validation

import android.util.Patterns
import com.example.healthc.domain.model.validation.ValidationResult

class ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationResult{
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "이메일을 입력해주세요."
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "이메일 형식을 확인해주세요."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}