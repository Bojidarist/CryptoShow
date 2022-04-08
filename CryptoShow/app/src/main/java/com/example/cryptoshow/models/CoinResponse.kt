package com.example.cryptoshow.models

import com.google.gson.annotations.SerializedName

data class CoinResponse(
    val id: String,
    val name: String,

    @SerializedName("market_data")
    val marketData: MarketData
)
