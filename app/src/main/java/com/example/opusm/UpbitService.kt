package com.example.opusm

import com.example.opusm.model.Coin
import com.example.opusm.model.TickerData
import retrofit2.http.GET
import retrofit2.http.Query

interface UpbitService {

    @GET("v1/market/all")
    suspend fun getAllCoins(): List<Coin>

    @GET("v1/ticker")
    suspend fun getTicker(@Query("markets") markets: String): List<TickerData>
}
