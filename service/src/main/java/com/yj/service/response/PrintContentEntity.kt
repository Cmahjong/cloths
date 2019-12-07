package com.yj.service.response

/**
 * desc:
 * time: 2019/12/7
 * @author yinYin
 */
data class PrintContentEntity(
    val code: Int?,
    val list: List<String?>?,
    val msg: String?,
    val time: String?
) {
    override fun toString(): String {
        return "PrintContentEntity(code=$code, list=$list, msg=$msg, time=$time)"
    }
}