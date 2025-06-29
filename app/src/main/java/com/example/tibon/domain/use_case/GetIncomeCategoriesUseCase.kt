package com.example.tibon.domain.use_case

import com.example.tibon.data.local.IncomeCategory
import com.example.tibon.domain.repository.TibonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIncomeCategoriesUseCase @Inject constructor(
    private val repository: TibonRepository
) {
    operator fun invoke(): Flow<List<IncomeCategory>> {
        return repository.getAllIncomeCategories()
    }
}