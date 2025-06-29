package com.example.tibon.domain.use_case

import com.example.tibon.domain.repository.TibonRepository
import com.example.tibon.presentation.ui.viewmodel.UiState
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchRatesUseCase @Inject constructor(
    private val repository: TibonRepository
) {
    operator fun invoke(baseCurrency: String): Flow<UiState<Map<String, Double>>> = flow {
        try {
            emit(UiState.Loading)
            val response = repository.getLatestRates(baseCurrency)
            emit(UiState.Success(response.conversionRates))
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            emit(UiState.Error(e.localizedMessage ?: "Terjadi kesalahan yang tidak diketahui"))
        }
    }
}