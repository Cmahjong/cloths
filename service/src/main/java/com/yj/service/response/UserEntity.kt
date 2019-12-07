package com.yj.service.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * desc:
 * time: 2019/3/21
 * @author yinYin
 */
@Parcelize
data class UserEntity(
    val code: Int?,
    val list: Data1?,
    val msg: String?,
    val time: String?
): Parcelable

@Parcelize
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
    val village: String?,
    var token: String?
): Parcelable