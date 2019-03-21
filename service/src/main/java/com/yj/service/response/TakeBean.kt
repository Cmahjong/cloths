package com.yj.service.response

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
data class TakeBean(
    val code: Int?,
    val list: Data?,
    val msg: String?,
    val time: String?
)

data class Data(
    val cabinetInfo: CabinetInfo?,
    val code: Int?,
    val emptyBox: List<OrderBox?>?,
    val orderBox: List<OrderBox?>?,
    val orderCount: String?
)

data class OrderBox(
    val boxId: String?,
    val boxName: String?,
    val boxNumber: String?,
    val cabinetId: String?,
    val isEmpty: String?,
    var onOff: String?,
    val orderId: String?,
    val pickupCode: Any?
)

data class CabinetInfo(
    val address: String?,
    val area: String?,
    val cabinetId: String?,
    val cabinetName: String?,
    val city: String?,
    val province: String?,
    val shopId: String?,
    val staffId: String?,
    val status: String?,
    val time: String?
)