package com.example.tibon.domain.use_case

import com.example.tibon.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveCurrencySettingUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(code: String, symbol: String) {
        repository.saveCurrencySetting(code, symbol)
    }
}