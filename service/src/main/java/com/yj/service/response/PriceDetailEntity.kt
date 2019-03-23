package com.yj.service.response

/**
 * desc:
 * time: 2019/3/23
 * @author yinYin
 */
data class PriceDetailEntity(
    val code: Int?,
    val count: String?,
    val list: List<Data6?>?,
    val msg: String?,
    val price: String?,
    val time: String?
)

data class Data6(
    var className: String?,
    var detailName: String?,
    val id: String?,
    var number: String?,
    val orderId: String?,
    val price: String?,
    val priceId: String?,
    val unit: String?
)