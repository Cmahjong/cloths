package com.yj.service.response

/**
 * desc:
 * time: 2019/3/25
 * @author yinYin
 */
data class PutEntity(
    val code: Int?,
    val list: PutList?,
    val msg: String?,
    val time: String?
)

data class PutList(
    val cabinetInfo: CabinetInfo?,
    val code: Int?,
    val order: List<PutOrder?>?,
    val orderCount: String?
)

data class PutOrder(
    val advanceCharge: String?,
    val creatTime: String?,
    val createBox: String?,
    val createCabinet: String?,
    val createCustomerId: String?,
    var finalBox: String?,
    var boxNumber: String?,
    val finalCabinet: Any?,
    val finalCustomerId: Any?,
    val finalTime: Any?,
    val orderId: String?,
    val pieceNumber: String?,
    val shopId: String?,
    val staffId: String?,
    var status: String?,
    var isEnable: Boolean?,
    val tailCharge: String?,
    val totalCharge: String?
)

