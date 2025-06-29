package com.example.tibon.domain.use_case

import com.example.tibon.data.local.Account
import com.example.tibon.domain.repository.TibonRepository
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val repository: TibonRepository
) {
    suspend operator fun invoke(account: Account) {
        val transactionsToDelete = repository.getTransactionsForAccountOnce(account.id)

        transactionsToDelete.forEach { transaction ->
            repository.deleteTransactionFromRealtimeDatabase(transaction.id)
        }

        repository.deleteAccountFromRealtimeDatabase(account.id)

        repository.deleteAccount(account)

        // Log event ke Firebase Analytics
        Firebase.analytics.logEvent("delete_account") {
            param("account_id", account.id.toLong())
            param("account_name", account.name)
        }
    }
}