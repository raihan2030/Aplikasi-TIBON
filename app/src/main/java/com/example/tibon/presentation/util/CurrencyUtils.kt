package com.example.tibon.presentation.util

import com.example.tibon.domain.model.CurrencySetting // Pastikan import ini ada
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun formatCurrency(
    value: Double,
    setting: CurrencySetting,
    conversionRate: Double?
): String {
    if (conversionRate == null) {
        return "${setting.symbol} --"
    }

    val convertedValue = value * conversionRate

    return try {
        val format = NumberFormat.getCurrencyInstance()
        format.currency = Currency.getInstance(setting.code)
        format.format(convertedValue)
    } catch (e: Exception) {
        String.format(Locale.getDefault(), "%s%.2f", setting.symbol, convertedValue)
    }
}