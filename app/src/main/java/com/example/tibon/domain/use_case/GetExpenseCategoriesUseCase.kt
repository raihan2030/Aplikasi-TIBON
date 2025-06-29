package com.example.tibon.domain.use_case

import com.example.tibon.data.local.ExpenseCategory
import com.example.tibon.domain.repository.TibonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpenseCategoriesUseCase @Inject constructor(
    private val repository: TibonRepository
) {
    operator fun invoke(): Flow<List<ExpenseCategory>> {
        return repository.getAllExpenseCategories()
    }
}