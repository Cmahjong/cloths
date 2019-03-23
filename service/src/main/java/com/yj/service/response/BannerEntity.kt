package com.yj.service.response

/**
 * desc:
 * time: 2019/3/23
 * @author yinYin
 */
data class BannerEntity(
    val code: Int?,
    val list: List<Data5?>?,
    val msg: String?,
    val time: String?
)

data class Data5(
    val application: String?,
    val banner_id: String?,
    val number: String?,
    val pictureUrl: String?
)