package com.yj.clothsdemo.util

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * desc:
 * time: 2018/11/29
 * @author yinYin
 */
fun <T> Observable<T>.threadSwitch(): Observable<T> {
    return this.compose { upstream ->
        upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}