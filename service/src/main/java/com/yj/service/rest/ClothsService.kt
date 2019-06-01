package com.yj.service.rest

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


    @GET("https://wash.huidangchina.com/")
    fun login(@Query("service") service: String, @Query("account") account: String, @Query("password") password: String): Observable<UserEntity>

    @GET("https://wash.huidangchina.com/")
    fun take(@Query("service") service: String, @Query("token") token: String, @Query("cabinetId") cabinetId: String): Observable<TakeBean>

    @GET("https://wash.huidangchina.com/")
    fun put(@Query("service") service: String, @Query("token") token: String, @Query("cabinetId") cabinetId: String): Observable<PutEntity>

    @GET("https://wash.huidangchina.com/")
    fun ensure(@Query("service") service: String, @Query("token") token: String, @Query("boxId") boxId: String, @Query("cabinetId") cabinetId: String, @Query("orderId") orderId: String): Observable<SaveEntity>

    @GET("https://wash.huidangchina.com/")
    fun boxNum(@Query("service") service: String, @Query("token") token: String, @Query("cabinetId") cabinetId: String): Observable<BoxNumEntity>

    @GET("https://wash.huidangchina.com/")
    fun open(@Query("service") service: String, @Query("token") token: String, @Query("boxId") boxId: String): Observable<OpenEntity>

    @GET("https://wash.huidangchina.com/")
    fun isOrder(@Query("service") service: String, @Query("token") token: String, @Query("cabinetId") boxId: String): Observable<Boolean>

    @GET("https://wash.huidangchina.com/")
    fun priceList(@Query("service") service: String, @Query("token") token: String): Observable<PriceEntity>

    @GET("https://wash.huidangchina.com/")
    fun takeRecode(@Query("service") service: String, @Query("token") token: String): Observable<TakeRecodeEntity>

    @GET("https://wash.huidangchina.com/")
    fun putRecode(@Query("service") service: String, @Query("token") token: String): Observable<PutRecodeEntity>

    @GET("https://wash.huidangchina.com/")
    fun banner(@Query("service") service: String, @Query("token") token: String): Observable<BannerEntity>

    @GET("https://wash.huidangchina.com/")
    fun longBanner(@Query("service") service: String, @Query("token") token: String): Observable<BannerEntity>

    @GET("https://wash.huidangchina.com/")
    fun priceDetail(@Query("service") service: String, @Query("token") token: String, @Query("orderId") orderId: String): Observable<PriceDetailEntity>
    @GET("https://wash.huidangchina.com/")
    fun delete(@Query("service") service: String, @Query("token") token: String, @Query("id") id: String): Observable<DeleteEntity>


    @GET("https://wash.huidangchina.com/")
    fun add(@Query("service") service: String, @Query("token") token: String, @Query("orderId") id: String): Observable<AddEntity>

    @GET("https://wash.huidangchina.com/")
    fun save(@Query("service") service: String, @Query("token") token: String, @Query("orderId") id: String): Observable<SaveEntity>

    @GET("https://wash.huidangchina.com/")
    fun change(@Query("service") service: String, @Query("token") token: String, @Query("id") id: String, @Query("number") number: String, @Query("unit") unit: String, @Query("price") price: String, @Query("priceId") priceId: String): Observable<SaveEntity>

    @GET("https://wash.huidangchina.com/")
    fun bigType(@Query("service") service: String, @Query("token") token: String, @Query("shopId") shopId: String): Observable<BigTypeEntity>

    @GET("https://wash.huidangchina.com/")
    fun smallType(@Query("service") service: String, @Query("token") token: String, @Query("className") className: String, @Query("shopId") shopId: String): Observable<SmallTypeEntity>
}