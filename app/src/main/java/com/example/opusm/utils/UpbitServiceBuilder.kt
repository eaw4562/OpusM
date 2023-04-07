package com.example.opusm.utils

import com.example.opusm.UpbitService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object UpbitServiceBuilder {
    private const val BASE_URL = "https://api.upbit.com/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS) // 연결 시간 초과: 10초
        .readTimeout(10, TimeUnit.SECONDS) // 읽기 시간 초과: 10초
        .writeTimeout(10, TimeUnit.SECONDS) // 쓰기 시간 초과: 10초
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: UpbitService = retrofit.create(UpbitService::class.java)

}
