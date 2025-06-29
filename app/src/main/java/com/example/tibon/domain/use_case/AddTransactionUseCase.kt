package com.example.tibon.domain.use_case

import com.example.tibon.data.local.Transaction
import com.example.tibon.domain.repository.TibonRepository
import java.util.Date
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: TibonRepository
) {
    suspend operator fun invoke(
        accountId: Int,
        amount: Double,
        type: String,
        category: String,
        description: String?,
        date: Date
    ) {
        val transaction = Transaction(
            accountId = accountId,
            amount = amount,
            type = type,
            category = category,
            description = description?.ifEmpty { null },
            date = date
        )
        repository.addTransaction(transaction)
    }
}