package com.yj.service.response

/**
 * desc:
 * time: 2019/3/21
 * @author yinYin
 */
data class UserEntity(
    val code: Int?,
    val list: Data1?,
    val msg: String?,
    val time: String?
)

data class Data1(
    val account: String?,
    val area: String?,
    val city: String?,
    val code: Int?,
    val entryTime: String?,
    val mobile: String?,
    val name: String?,
    val password: String?,
    val picture: String?,
    val province: String?,
    val sex: String?,
    val staffId: String?,
    val token: String?
)