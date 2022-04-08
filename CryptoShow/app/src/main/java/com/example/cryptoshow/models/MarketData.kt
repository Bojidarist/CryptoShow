package com.example.cryptoshow.models

import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("current_price")
    val currentPrice: CoinPrice,
    @SerializedName("low_24h")
    val low: CoinPrice,
    @SerializedName("high_24h")
    val high: CoinPrice,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Float,
)
