package com.yj.clothsdemo.util

import android.view.View

/**
 * desc:
 * time: 2018/11/29
 * @author yinYin
 */
fun View.onClick(function: () -> Unit) {
    this.setOnClickListener {
        function()
    }
}