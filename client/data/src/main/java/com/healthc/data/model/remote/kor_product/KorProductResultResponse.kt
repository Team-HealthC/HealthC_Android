package com.healthc.data.model.remote.kor_product

import com.healthc.domain.model.kor_product.KorProduct

data class KorProductResultResponse(
    val header: KorProductResponseHeader,
    val body: KorProductResponseBody
)

data class KorProductResponseHeader(
    val resultCode: String,
    val resultMessage: String
)

data class KorProductResponseBody(
    val items: List<KorProductResponseItem>,
    val totalCount: String,
    val pageNo: String,
    val numOfRows: String
)

data class KorProductResponseItem(
    val item: KorProductResponse
)

data class KorProductResponse(
    val allergy: String,
    val barcode: String,
    val imgurl1: String,
    val imgurl2: String,
    val manufacture: String,
    val nutrient: String?,
    val prdkind: String,
    val prdkindstate: String,
    val prdlstNm: String,
    val prdlstReportNo: String,
    val productGb: String,
    val rawmtrl: String,
    val rnum: String,
    val seller: String
){
    fun toDomain() : KorProduct = KorProduct(
        id = prdlstReportNo,
        allergies = allergy,
        image = imgurl1,
        nutrient = nutrient ?: "",
        category = prdkind,
        name = prdlstNm,
        rawMaterials = rawmtrl
    )
}