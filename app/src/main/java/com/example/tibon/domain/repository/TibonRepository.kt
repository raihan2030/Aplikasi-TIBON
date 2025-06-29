package com.example.tibon.domain.repository

import com.example.tibon.data.local.Account
import com.example.tibon.data.local.ExpenseCategory
import com.example.tibon.data.local.IncomeCategory
import com.example.tibon.data.local.NotificationHistory
import com.example.tibon.data.local.Transaction
import com.example.tibon.data.remote.ExchangeRateResponse
import kotlinx.coroutines.flow.Flow

interface TibonRepository {
    // Fungsi untuk Room DB
    fun getAllAccounts(): Flow<List<Account>>
    fun getAccountById(id: Int): Flow<Account?>
    fun getBalanceForAccount(id: Int): Flow<Double?>
    fun getAllTransactions(): Flow<List<Transaction>>
    fun getTransactionsForAccount(id: Int): Flow<List<Transaction>>
    suspend fun getTransactionsForAccountOnce(id: Int): List<Transaction>
    suspend fun addAccount(account: Account): Long
    suspend fun updateAccount(account: Account)
    suspend fun deleteAccount(account: Account)
    suspend fun addTransactionAndGetId(transaction: Transaction): Long
    fun getAllIncomeCategories(): Flow<List<IncomeCategory>>
    fun getAllExpenseCategories(): Flow<List<ExpenseCategory>>
    fun getAllNotificationHistory(): Flow<List<NotificationHistory>>

    // Fungsi untuk Realtime Database - Rekening
    suspend fun saveAccountToRealtimeDatabase(account: Account)
    suspend fun updateAccountInRealtimeDatabase(account: Account)
    suspend fun deleteAccountFromRealtimeDatabase(accountId: Int)

    // Fungsi untuk Realtime Database - Transaksi
    suspend fun saveTransactionToRealtimeDatabase(transaction: Transaction)
    suspend fun deleteTransactionFromRealtimeDatabase(transactionId: Int)

    // Fungsi untuk Retrofit API
    suspend fun getLatestRates(base: String): ExchangeRateResponse
}