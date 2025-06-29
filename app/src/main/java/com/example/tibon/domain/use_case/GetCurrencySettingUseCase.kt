package com.example.tibon.domain.use_case

import com.example.tibon.domain.model.CurrencySetting
import com.example.tibon.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencySettingUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<CurrencySetting> = repository.currencySetting
}