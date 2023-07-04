package com.example.healthc.data.dto.kor_product

import com.example.healthc.domain.model.kor_product.KorProduct
import com.example.healthc.domain.model.kor_product.KorProductInfo


data class KorProductResponse(
    val body: KorProductDto,
    val header: ProductHeaderDto
)

data class KorProductDto(
    val items: List<ProductItemDto>,
    val totalCount: String,
    val pageNo: String,
    val numOfRows: String
    ){
    fun toKorProduct(): KorProduct = KorProduct(
        items = items.map{ it.item.toProductInfo() },
        numOfRows = numOfRows,
        pageNo = pageNo,
        totalCount = totalCount
    )
}

data class ProductHeaderDto(
    val resultCode: String,
    val resultMessage: String
)

data class ProductItemDto(
    val item: ProductItemXDto
)

data class ProductItemXDto(
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

    fun toProductInfo() : KorProductInfo = KorProductInfo(
        allergies = allergy,
        image = imgurl1,
        nutrient = nutrient ?: "",
        category = prdkind,
        name = prdlstNm,
        rawMaterials = rawmtrl
    )
}