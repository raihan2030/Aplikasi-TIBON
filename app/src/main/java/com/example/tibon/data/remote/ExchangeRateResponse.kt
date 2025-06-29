package com.example.tibon.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExchangeRateResponse(
    @field:Json(name = "result")
    val result: String,
    @field:Json(name = "base_code")
    val baseCode: String,
    @field:Json(name = "conversion_rates")
    val conversionRates: Map<String, Double>
)