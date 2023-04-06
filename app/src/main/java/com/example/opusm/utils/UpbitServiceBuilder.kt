package com.example.opusm.utils

import com.example.opusm.UpbitService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UpbitServiceBuilder {
    private const val BASE_URL = "https://api.upbit.com/"

    private val client = OkHttpClient.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: UpbitService = retrofit.create(UpbitService::class.java)

}
