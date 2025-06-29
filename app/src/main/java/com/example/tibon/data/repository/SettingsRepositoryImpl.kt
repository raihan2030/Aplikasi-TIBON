package com.example.tibon.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tibon.domain.model.CurrencySetting
import com.example.tibon.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): SettingsRepository {

    private object PreferencesKeys {
        val CURRENCY_CODE = stringPreferencesKey("currency_code")
        val CURRENCY_SYMBOL = stringPreferencesKey("currency_symbol")
    }

    override val currencySetting: Flow<CurrencySetting> = context.dataStore.data
        .map { preferences ->
            val code = preferences[PreferencesKeys.CURRENCY_CODE] ?: "IDR"
            val symbol = preferences[PreferencesKeys.CURRENCY_SYMBOL] ?: "Rp"
            CurrencySetting(code, symbol)
        }

    override suspend fun saveCurrencySetting(code: String, symbol: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENCY_CODE] = code
            preferences[PreferencesKeys.CURRENCY_SYMBOL] = symbol
        }
    }
}