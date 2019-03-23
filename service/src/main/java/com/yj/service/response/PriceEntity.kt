package com.yj.service.response

/**
 * desc:
 * time: 2019/3/23
 * @author yinYin
 */
data class PriceEntity(
    val code: Int?,
    val list: Data3?,
    val msg: String?,
    val time: String?
)

data class Data3(
    val code: Int?,
    val list: List<Price?>?
)

data class Price(
    val cabinetName: String?,
    val orderID: String?,
    val pieceNumber: String?,
    val shopId: String?,
    val status: String?
)