package com.example.tibon.domain.use_case

import com.example.tibon.data.local.Account
import com.example.tibon.data.local.Transaction
import com.example.tibon.domain.repository.TibonRepository
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import java.util.Date
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(
    private val repository: TibonRepository
) {
    suspend operator fun invoke(name: String, initialBalance: Double, notes: String?, type: String) {
        val account = Account(name = name, notes = notes, type = type)
        val accountId = repository.addAccount(account)
        if (initialBalance > 0) {
            val transaction = Transaction(
                accountId = accountId.toInt(),
                amount = initialBalance,
                type = "Pemasukan",
                category = "Saldo Awal",
                description = "Saldo Awal",
                date = Date()
            )
            repository.addTransaction(transaction)
        }

        //Log event ke Firebase Analytics
        Firebase.analytics.logEvent("add_account") {
            param("account_type", type)
            param("with_initial_balance", (initialBalance > 0).toString())
        }
    }
}