package com.example.cryptoshow.services

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class CoinPrice(val usd: Float)
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

data class CoinResponse(
    val id: String,
    val name: String,

    @SerializedName("market_data")
    val marketData: MarketData
)

interface CoinGeckoService {
    @GET("coins/{coin}")
    fun getCoin(@Path("coin") coin: String?): Call<CoinResponse>
}