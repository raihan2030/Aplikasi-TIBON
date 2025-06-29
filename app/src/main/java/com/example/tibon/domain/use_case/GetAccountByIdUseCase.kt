package com.example.tibon.domain.use_case

import com.example.tibon.data.local.Account
import com.example.tibon.domain.repository.TibonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountByIdUseCase @Inject constructor(
    private val repository: TibonRepository
) {
    operator fun invoke(id: Int): Flow<Account?> {
        return repository.getAccountById(id)
    }
}