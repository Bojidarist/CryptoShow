package com.example.cryptoshow

import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.cryptoshow.models.CoinResponse
import com.example.cryptoshow.services.HttpClient
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchField = findViewById<TextInputEditText?>(R.id.searchField)
        val coinDetailsContainer = findViewById<CardView?>(R.id.coinDetailsContainer)
        val coinName = findViewById<TextView?>(R.id.coinName)
        val coinPrice = findViewById<TextView?>(R.id.coinPrice)
        val coinPriceChange = findViewById<TextView?>(R.id.coinPriceChange)
        val coinPriceLow = findViewById<TextView?>(R.id.coinPriceLow)
        val coinPriceHigh = findViewById<TextView?>(R.id.coinPriceHigh)
        val searchButton = findViewById<Button?>(R.id.searchButton)

        searchButton.setOnClickListener {
            searchButton.isEnabled = false
            HttpClient.getHttpClient().getCoin(searchField.text?.toString()).enqueue(object: Callback<CoinResponse> {
                override fun onResponse(call: Call<CoinResponse>, response: Response<CoinResponse>) {
                    if (response.isSuccessful)
                    {
                        val coinResponse = response.body()

                        coinDetailsContainer.visibility = View.VISIBLE
                        coinName.text = coinResponse?.name
                        coinPrice.text = "Price: $${coinResponse?.marketData?.currentPrice?.usd}"

                        val priceChange = coinResponse?.marketData?.priceChangePercentage24h
                        priceChange?.let {
                            when {
                                it < 0 -> {
                                    coinPriceChange.setTextColor(Color.RED)
                                }
                                it > 0 -> {
                                    coinPriceChange.setTextColor(Color.GREEN)
                                }
                                else -> {
                                    coinPriceChange.setTextColor(Color.BLACK)
                                }
                            }
                            coinPriceChange.text = it.toString()
                        }

                        coinPriceLow.text = "Low(24h): $${coinResponse?.marketData?.low?.usd}"
                        coinPriceHigh.text = "High(24h): $${coinResponse?.marketData?.high?.usd}"
                    }
                    else {
                        coinDetailsContainer.visibility = View.GONE
                    }
                    searchButton.isEnabled = true
                }

                override fun onFailure(call: Call<CoinResponse>, t: Throwable) {
                    searchButton.isEnabled = true
                    coinDetailsContainer.visibility = View.GONE
                }

            })
        }
    }
}