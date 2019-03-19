package com.yj.clothsdemo

import android.support.multidex.MultiDexApplication
import com.yj.demo.service.Client

/**
 * desc:
 * time: 
 * @author yinYin
 */
class App : MultiDexApplication() {
    /** 初始化网络服务器 */
    val client: Client by lazy {
        Client()
    }
    override fun onCreate() {
        super.onCreate()
    }

}