package com.healthc.domain.usecase.validation

import com.healthc.domain.model.validation.ValidationResult

class ValidateNameUseCase {
    operator fun invoke(nickName: String): ValidationResult {
        if (nickName.length !in 2..9) {
            return ValidationResult(
                successful = false,
                errorMessage = "이름은 2글자 이상 9글자 이하로 입력해주세요."
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}