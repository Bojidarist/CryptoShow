package com.example.cryptoshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.cryptoshow.services.CoinResponse
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
        val coinName = findViewById<TextView?>(R.id.coinName)
        val coinPrice = findViewById<TextView?>(R.id.coinPrice)
        val searchButton = findViewById<Button?>(R.id.searchButton)

        searchButton.setOnClickListener {
            searchButton.isEnabled = false
            HttpClient.getHttpClient().getCoin(searchField.text?.toString()).enqueue(object: Callback<CoinResponse> {
                override fun onResponse(call: Call<CoinResponse>, response: Response<CoinResponse>) {
                    // Log.d("my-message", response.body()?.marketData?.currentPrice?.usd.toString())
                    if (response.isSuccessful)
                    {
                        coinName.text = response.body()?.name
                        coinPrice.text = response.body()?.marketData?.currentPrice?.usd.toString()
                    }
                    else {
                        coinName.text = "Not Found"
                        coinPrice.text = ""
                    }
                    searchButton.isEnabled = true
                }

                override fun onFailure(call: Call<CoinResponse>, t: Throwable) {
                    searchButton.isEnabled = true
                }

            })
        }
    }
}