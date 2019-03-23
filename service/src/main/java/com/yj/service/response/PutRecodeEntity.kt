package com.yj.service.response

/**
 * desc:
 * time: 2019/3/23
 * @author yinYin
 */
data class PutRecodeEntity(
    val code: Int?,
    val list: List<Data4?>?,
    val msg: String?,
    val time: String?
)

data class Data4(
    val Time: String?,
    val boxNumber: String?,
    val createName: String?,
    val finalName: String?,
    val operateTime: String?,
    val pieceNumber: String?,
    val recordId: String?,
    val same: Boolean?
)