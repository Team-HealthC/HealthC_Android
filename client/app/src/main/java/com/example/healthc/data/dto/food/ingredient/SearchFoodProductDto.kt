package com.example.healthc.data.dto.food.ingredient

import com.example.healthc.domain.model.food.*

data class SearchFoodProductDto(
    val body: ProductBodyDto,
    val header: ProductHeaderDto
){
    fun toSearchFoodProduct(): SearchFoodProduct = SearchFoodProduct(
        body = body.toProductBody(),
        header = header.toProductHeader()
    )
}

data class ProductBodyDto(
    val items: List<ProductItemDto>,
    val totalCount: String,
    val pageNo: String,
    val numOfRows: String
    ){
    fun toProductBody(): ProductBody = ProductBody(
        items = items.map{ it.item.toProductItemX() },
        numOfRows = numOfRows,
        pageNo = pageNo,
        totalCount = totalCount
    )
}

data class ProductHeaderDto(
    val resultCode: String,
    val resultMessage: String
){
    fun toProductHeader() : ProductHeader = ProductHeader(
        resultCode = resultCode,
        resultMessage = resultMessage
    )
}

data class ProductItemDto(
    val item: ProductItemXDto
)

data class ProductItemXDto(
    val allergy: String,
    val barcode: String,
    val capacity: String,
    val imgurl1: String,
    val imgurl2: String,
    val manufacture: String,
    val nutrient: String,
    val prdkind: String,
    val prdkindstate: String,
    val prdlstNm: String,
    val prdlstReportNo: String,
    val productGb: String,
    val rawmtrl: String,
    val rnum: String,
    val seller: String
){
    constructor(): this("", "", "", "", "", "",
        "", "", "", "", "", "", "",
        "", "")

    fun toProductItemX() : ProductItem = ProductItem(
        allergy = allergy,
        capacity = capacity,
        imgurl1 = imgurl1,
        manufacture = manufacture,
        nutrient = nutrient,
        prdkind = prdkind,
        prdlstNm = prdlstNm,
        rawmtrl = rawmtrl,
        rnum = rnum
    )
}