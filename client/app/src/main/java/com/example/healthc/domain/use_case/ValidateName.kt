package com.example.healthc.domain.use_case

import com.example.healthc.domain.model.validate.ValidationResult

class ValidateName {
    operator fun invoke(nickName: String): ValidationResult {
        if (nickName.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "이름은 2글자 이상으로 입력해주세요."
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}