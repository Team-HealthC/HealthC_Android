package com.example.healthc.domain.model.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)