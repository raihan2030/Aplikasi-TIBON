package com.example.tibon.domain.use_case

import com.example.tibon.data.local.Account
import com.example.tibon.domain.repository.TibonRepository
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val repository: TibonRepository
) {
    suspend operator fun invoke(account: Account) {
        repository.updateAccount(account)


        // Log event ke Firebase Analytics
        Firebase.analytics.logEvent("edit_account") {
            param("account_id", account.id.toLong())
            param("account_name", account.name)
        }
    }
}