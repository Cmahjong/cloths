package com.yj.service.response

/**
 * desc:
 * time: 2019/3/23
 * @author yinYin
 */
data class BigTypeEntity(
    val code: Int?,
    val list: Data7?,
    val msg: String?,
    val time: String?
)

data class Data7(
    val classList: List<Data8?>?,
    val code: Int?
)

data class Data8(
    val className: String?
)