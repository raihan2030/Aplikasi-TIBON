package com.example.tibon.domain.use_case

import com.example.tibon.data.local.Transaction
import com.example.tibon.domain.repository.TibonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsForAccountUseCase @Inject constructor(
    private val repository: TibonRepository
) {
    operator fun invoke(accountId: Int): Flow<List<Transaction>> {
        return repository.getTransactionsForAccount(accountId)
    }
}