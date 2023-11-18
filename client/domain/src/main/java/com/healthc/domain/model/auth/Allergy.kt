package com.healthc.domain.model.auth

data class Allergy(
    val allergy: String
){
    constructor(): this("")
    fun toEnglish(): Allergy {
        return when (this) {
            Allergy("돼지고기") -> { Allergy("pork") }
            Allergy("대두") -> { Allergy("soybean") }
            Allergy("땅콩") -> { Allergy("peanut") }
            Allergy("계란") -> { Allergy("egg") }
            Allergy("우유") -> { Allergy("milk") }
            Allergy("밀") -> { Allergy("flour") }
            Allergy("복숭아") -> { Allergy("peach") }
            Allergy("견과류") -> { Allergy("nuts") }
            Allergy("갑각류") -> { Allergy("crab") }
            Allergy("조개류") -> { Allergy("clam") }
            Allergy("생선") -> { Allergy("fish") }
            else -> { this }
        }
    }
}