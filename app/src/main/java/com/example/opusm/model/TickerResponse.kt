package com.example.opusm.model

data class TickerResponse(
    val success: Boolean,
    val data: List<TickerData>
)
