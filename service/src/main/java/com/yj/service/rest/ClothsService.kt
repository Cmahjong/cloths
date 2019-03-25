package com.yj.service.rest

import com.yj.service.Client.Companion.BASE_URL
import com.yj.service.response.*
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

    @GET(BASE_URL)
    fun takeRecode(@Query("service") service: String, @Query("token") token: String): Observable<TakeRecodeEntity>

    @GET(BASE_URL)
    fun putRecode(@Query("service") service: String, @Query("token") token: String): Observable<PutRecodeEntity>

    @GET(BASE_URL)
    fun banner(@Query("service") service: String, @Query("token") token: String): Observable<BannerEntity>

    @GET(BASE_URL)
    fun longBanner(@Query("service") service: String, @Query("token") token: String): Observable<BannerEntity>

    @GET(BASE_URL)
    fun priceDetail(@Query("service") service: String, @Query("token") token: String, @Query("orderId") orderId: String): Observable<PriceDetailEntity>
    @GET(BASE_URL)
    fun delete(@Query("service") service: String, @Query("token") token: String, @Query("id") id: String): Observable<DeleteEntity>


    @GET(BASE_URL)
    fun add(@Query("service") service: String, @Query("token") token: String, @Query("orderId") id: String): Observable<AddEntity>

    @GET(BASE_URL)
    fun save(@Query("service") service: String, @Query("token") token: String, @Query("orderId") id: String): Observable<SaveEntity>

    @GET(BASE_URL)
    fun change(@Query("service") service: String, @Query("token") token: String, @Query("id") id: String, @Query("number") number: String, @Query("unit") unit: String, @Query("price") price: String, @Query("priceId") priceId: String): Observable<SaveEntity>

    @GET(BASE_URL)
    fun bigType(@Query("service") service: String, @Query("token") token: String, @Query("shopId") shopId: String): Observable<BigTypeEntity>

    @GET(BASE_URL)
    fun smallType(@Query("service") service: String, @Query("token") token: String, @Query("className") className: String, @Query("shopId") shopId: String): Observable<SmallTypeEntity>
}