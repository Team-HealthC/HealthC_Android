package com.healthc.domain.model.detection

import com.healthc.domain.model.auth.Allergy

data class AllergyTextDetection (
    val allergies: List<Allergy>,
    val recognizedText: String,
)