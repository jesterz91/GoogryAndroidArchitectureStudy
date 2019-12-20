package com.example.kotlinapplication.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val API_BASE_URL = "https://openapi.naver.com/"

    private lateinit var retrofit: Retrofit

    val client: RetrofitService by lazy {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("X-Naver-Client-Id", "o4YDMUoQQ3B5C1aJtX2g")
                .addHeader("X-Naver-Client-Secret", "FrW3bUTwCa")
                .build()
            chain.proceed(request)

        }
        builder.addInterceptor(interceptor)
        val okHttpClient =
            builder.connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build()
        retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        return@lazy retrofit.create(RetrofitService::class.java)
    }
}