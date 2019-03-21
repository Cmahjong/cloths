package com.yj.service.response

/**
 * desc:
 * time: 2019/3/21
 * @author yinYin
 */
data class OpenEntity(
    val code: Int?,
    val list: Data2?,
    val msg: String?,
    val time: String?
)

data class Data2(
    val boxId: String?,
    val boxName: String?,
    val boxNumber: String?,
    val cabinetId: String?,
    val code: Int?,
    val isEmpty: String?,
    val onOff: String?,
    val orderId: String?,
    val pickupCode: Any?
)