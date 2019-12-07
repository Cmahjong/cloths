package com.yj.clothsdemo.util

import android.content.Context
import android.widget.Toast

/**
 * desc:
 * time: 2019/3/19
 * @author yinYin
 */
object ToastUtils {
    fun show(context: Context?,msg: String?) {
        if (msg.isNullOrBlank()||context==null) {
            return
        }
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }
    fun showLong(context: Context?,msg: String?) {
        if (msg.isNullOrBlank()||context==null) {
            return
        }
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
    }
}