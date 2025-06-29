package com.example.tibon.domain.repository

import com.example.tibon.domain.model.CurrencySetting
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val currencySetting: Flow<CurrencySetting>
    suspend fun saveCurrencySetting(code: String, symbol: String)
}