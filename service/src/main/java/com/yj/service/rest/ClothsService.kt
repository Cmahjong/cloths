package com.yj.service.rest

import com.yj.service.Client.Companion.BASE_URL
import com.yj.service.response.OpenEntity
import com.yj.service.response.PriceEntity
import com.yj.service.response.TakeBean
import com.yj.service.response.UserEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * desc:
 * time: 2018/11/29
 * @author yinYin
 */
interface ClothsService {


    @GET(BASE_URL)
    fun login(@Query("service") service: String, @Query("account") account: String, @Query("password") password: String): Observable<UserEntity>

    @GET(BASE_URL)
    fun take(@Query("service") service: String, @Query("token") token: String, @Query("cabinetId") cabinetId: String): Observable<TakeBean>

    @GET(BASE_URL)
    fun open(@Query("service") service: String, @Query("token") token: String, @Query("boxId") boxId: String): Observable<OpenEntity>

    @GET(BASE_URL)
    fun isOrder(@Query("service") service: String, @Query("token") token: String, @Query("cabinetId") boxId: String): Observable<Boolean>

    @GET(BASE_URL)
    fun priceList(@Query("service") service: String, @Query("token") token: String): Observable<PriceEntity>

}