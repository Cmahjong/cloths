package com.yj.service.response

/**
 * desc:
 * time: 2019/3/23
 * @author yinYin
 */
data class SmallTypeEntity(
    val code: Int?,
    val list: Data9?,
    val msg: String?,
    val time: String?
)

data class Data9(
    val classList: List<Data10?>?,
    val code: Int?
)

data class Data10(
    val detailName: String?,
    val price: String?,
    val priceId: String?,
    val unit: String?
)