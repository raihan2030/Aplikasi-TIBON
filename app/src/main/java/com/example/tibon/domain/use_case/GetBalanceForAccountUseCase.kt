package com.example.tibon.domain.use_case

import com.example.tibon.domain.repository.TibonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBalanceForAccountUseCase @Inject constructor(
    private val repository: TibonRepository
) {
    operator fun invoke(accountId: Int): Flow<Double?> {
        return repository.getBalanceForAccount(accountId)
    }
}