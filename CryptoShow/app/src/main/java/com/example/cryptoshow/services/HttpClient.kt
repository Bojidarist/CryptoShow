package com.example.cryptoshow.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object HttpClient {
    fun getHttpClient() : CoinGeckoService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.coingecko.com/api/v3/")
            .build()

        return retrofit.create(CoinGeckoService::class.java)
    }
}