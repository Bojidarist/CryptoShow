package com.example.cryptoshow

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private var searchField: TextInputEditText? = null
    private var coinDetailsContainer: CardView? = null
    private var coinName: TextView? = null
    private var coinPrice: TextView? = null
    private var coinPriceChange: TextView? = null
    private var coinPriceLow: TextView? = null
    private var coinPriceHigh: TextView? = null
    private var searchButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchField = findViewById(R.id.searchField)
        coinDetailsContainer = findViewById(R.id.coinDetailsContainer)
        coinName = findViewById(R.id.coinName)
        coinPrice = findViewById(R.id.coinPrice)
        coinPriceChange = findViewById(R.id.coinPriceChange)
        coinPriceLow = findViewById(R.id.coinPriceLow)
        coinPriceHigh = findViewById(R.id.coinPriceHigh)
        searchButton = findViewById(R.id.searchButton)

        setListeners()
    }

    private fun setListeners(){
        searchButton?.setOnClickListener {
            searchButton?.isEnabled = false
            handleSearchButtonClick()
            searchButton?.isEnabled = true
        }
    }

    private fun handleSearchButtonClick(){
        HttpClient.getHttpClient().getCoin(searchField?.text.toString()).enqueue(object: Callback<CoinResponse> {
            override fun onResponse(call: Call<CoinResponse>, response: Response<CoinResponse>) {
                if (response.isSuccessful){
                    setCoinInfo(response.body())
                }
                else {
                    coinDetailsContainer?.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<CoinResponse>, t: Throwable) {
                coinDetailsContainer?.visibility = View.GONE
            }
        })
    }

    private fun setCoinInfo(coinResponse: CoinResponse?){
        coinDetailsContainer?.visibility = View.VISIBLE
        coinName?.text = coinResponse?.name
        val price = "Price: $${coinResponse?.marketData?.currentPrice?.usd}"
        coinPrice?.text = price

        val priceChange = coinResponse?.marketData?.priceChangePercentage24h
        priceChange?.let {
            when {
                it < 0 -> {
                    coinPriceChange?.setTextColor(Color.RED)
                }
                it > 0 -> {
                    coinPriceChange?.setTextColor(Color.GREEN)
                }
                else -> {
                    coinPriceChange?.setTextColor(Color.BLACK)
                }
            }
            coinPriceChange?.text = it.toString()
        }

        val priceLow = "Low(24h): $${coinResponse?.marketData?.low?.usd}"
        val priceHigh = "High(24h): $${coinResponse?.marketData?.high?.usd}"
        coinPriceLow?.text = priceLow
        coinPriceHigh?.text = priceHigh
    }
}