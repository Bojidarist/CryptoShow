package com.example.cryptoshow.services

import com.example.cryptoshow.models.CoinResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinGeckoService {
    @GET("coins/{coin}")
    fun getCoin(@Path("coin") coin: String?): Call<CoinResponse>
}