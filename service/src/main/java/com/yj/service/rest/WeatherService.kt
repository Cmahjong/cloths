package com.yj.demo.service.rest

import com.yj.demo.service.response.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * desc:
 * time: 2018/11/29
 * @author yinYin
 */
interface WeatherService {
    @GET("/s6/weather/now")
    fun weatherDetail(@Query("location") location: String, @Query("key") key: String): Observable<WeatherResponse>

}