package com.example.healthc.domain.model.kor_product

data class KorProduct(
    val allergies: String, // 알러지 유발 물질
    val image: String, // 이미지 소스
    val nutrient: String, // 영양
    val category: String, // 제품 카테고리
    val name: String, // 제품 이름
    val rawMaterials : String, // 원재료 구성
)