package com.example.tibon.presentation.util

import com.example.tibon.domain.model.CurrencySetting // Pastikan import ini ada
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

// [DIPERBAIKI] Ubah parameter untuk menerima objek CurrencySetting
fun formatCurrency(
    value: Double,
    setting: CurrencySetting,
    conversionRate: Double?
): String {
    if (conversionRate == null) {
        return "${setting.symbol} --" // Tampilkan placeholder jika kurs belum tersedia
    }

    val convertedValue = value * conversionRate

    // Coba format berdasarkan Locale, fallback ke format manual jika tidak ada
    return try {
        val format = NumberFormat.getCurrencyInstance()
        format.currency = Currency.getInstance(setting.code)
        format.format(convertedValue)
    } catch (e: Exception) {
        // Fallback untuk simbol yang tidak standar atau kode yang tidak dikenal Locale
        String.format(Locale.getDefault(), "%s%.2f", setting.symbol, convertedValue)
    }
}