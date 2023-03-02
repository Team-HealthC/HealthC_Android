package com.example.healthc.domain.model.food

data class SearchFoodProduct(
    val body: ProductBody,
    val header: ProductHeader
)

data class ProductBody(
    val items: List<ProductItem>,
    val numOfRows: String,
    val pageNo: String,
    val totalCount: String
)

data class ProductHeader(
    val resultCode: String,
    val resultMessage: String
)

data class ProductItem(
    val item: ProductItemX
)

data class ProductItemX(
    val allergy: String, // 알러지 유발 물질
    val capacity: String, // 용량
    val imgurl1: String, // 이미지 소스
    val manufacture: String, // 제조사
    val nutrient: String, // 영양
    val prdkind: String, // 제품 카테고리
    val prdlstNm: String, // 제품 이름
    val rawmtrl: String, // 원재료 구성
    val rnum: String, // 번호
)