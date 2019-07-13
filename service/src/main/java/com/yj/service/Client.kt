package com.yj.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yj.service.rest.ClothsService
import com.yj.service.baseResponseConverter.BaseResponseConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import android.annotation.SuppressLint
import java.security.SecureRandom

import javax.net.ssl.*


/**
 * desc:
 * time: 2018/11/29
 * @author yinYin
 */
class Client {

    /** retrofit  */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(BaseResponseConverterFactory.create(createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    /** http client  */
    private val httpClient: OkHttpClient by lazy {
        createOkHttpClient()
    }
    var clothsService: ClothsService

    constructor() {
        clothsService = retrofit.create(ClothsService::class.java)
    }

    /**
     * 创建 ok http
     *
     * @param context
     *         上下文
     * @return ok http
     */
    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory()!!,TrustAllManager())
                .hostnameVerifier(TrustAllHostnameVerifier())
                .addInterceptor { chain ->
                    //本拦截器用于加headers
                    var request = chain.request()
                    val requestBuilder = request.newBuilder()
                    request = requestBuilder
                            .build()
                    chain.proceed(request)
                }
        if (true) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        builder
                .readTimeout(TIMEOUT_TIME.toLong(), TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_TIME.toLong(), TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(false)
        return builder.build()
    }


    /**
     * 创建gson
     *
     * @return gson
     */
    private fun createGson(): Gson {
        return GsonBuilder()
                .create()
    }

    companion object {
        /** base url */
        const val BASE_URL = "https://wash.huidangchina.com/"
        /** http请求的超时时间 */
        const val TIMEOUT_TIME = 30
        /**
         * 默认信任所有的证书
         * TODO 最好加上证书认证，主流App都有自己的证书
         *
         * @return
         */
        @SuppressLint("TrulyRandom")
         fun createSSLSocketFactory(): SSLSocketFactory? {

            var sSLSocketFactory: SSLSocketFactory? = null

            try {
                val sc = SSLContext.getInstance("TLS")
                sc.init(null, arrayOf<TrustManager>(TrustAllManager()),
                        SecureRandom())
                sSLSocketFactory = sc.socketFactory
            } catch (e: Exception) {
            }

            return sSLSocketFactory
        }
    }
}