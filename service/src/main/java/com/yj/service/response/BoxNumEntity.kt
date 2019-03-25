package com.yj.service.response

/**
 * desc:
 * time: 2019/3/25
 * @author yinYin
 */
data class BoxNumEntity(
    val code: Int?,
    val list: List<BoxNum?>?,
    val msg: String?,
    val time: String?
)

data class BoxNum(
    val boxId: String?,
    val boxNumber: String?
)