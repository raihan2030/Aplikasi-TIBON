package com.example.tibon.presentation.util

import com.example.tibon.domain.model.CurrencySetting

object AppConstants {
    val POPULAR_CURRENCIES = listOf(
        CurrencySetting("IDR", "Rp"),
        CurrencySetting("USD", "$"),
        CurrencySetting("EUR", "€"),
        CurrencySetting("JPY", "¥"),
        CurrencySetting("GBP", "£"),
        CurrencySetting("AUD", "A$"),
        CurrencySetting("CAD", "C$"),
        CurrencySetting("CNY", "¥"),
        CurrencySetting("SGD", "S$"),
        CurrencySetting("MYR", "RM")
    )
}