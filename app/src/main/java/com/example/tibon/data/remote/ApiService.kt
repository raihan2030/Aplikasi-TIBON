package com.example.tibon.data.remote

import com.example.tibon.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("v6/{apiKey}/latest/{baseCurrency}")
    suspend fun getLatestRates(
        @Path("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Path("baseCurrency") baseCurrency: String = "USD"
    ): ExchangeRateResponse
}