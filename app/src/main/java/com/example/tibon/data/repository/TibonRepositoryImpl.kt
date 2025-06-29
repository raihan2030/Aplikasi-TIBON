package com.example.tibon.data.repository

import com.example.tibon.data.local.Account
import com.example.tibon.data.local.AppDao
import com.example.tibon.data.local.ExpenseCategory
import com.example.tibon.data.local.IncomeCategory
import com.example.tibon.data.local.NotificationHistory
import com.example.tibon.data.local.Transaction
import com.example.tibon.data.remote.ApiService
import com.example.tibon.data.remote.ExchangeRateResponse
import com.example.tibon.domain.repository.TibonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TibonRepositoryImpl @Inject constructor(
    private val dao: AppDao,
    private val apiService: ApiService
) : TibonRepository {
    override fun getAllAccounts(): Flow<List<Account>> = dao.getAllAccounts()
    override fun getAccountById(id: Int): Flow<Account> = dao.getAccountById(id)
    override fun getBalanceForAccount(id: Int): Flow<Double?> = dao.getBalanceForAccount(id)
    override fun getAllTransactions(): Flow<List<Transaction>> = dao.getAllTransactions()
    override fun getTransactionsForAccount(id: Int): Flow<List<Transaction>> = dao.getTransactionsForAccount(id)
    override suspend fun addAccount(account: Account): Long = dao.insertAccount(account)
    override suspend fun updateAccount(account: Account) = dao.updateAccount(account)
    override suspend fun deleteAccount(account: Account) = dao.deleteAccount(account)
    override suspend fun addTransaction(transaction: Transaction) = dao.insertTransaction(transaction)
    override fun getAllIncomeCategories(): Flow<List<IncomeCategory>> = dao.getAllIncomeCategories()
    override fun getAllExpenseCategories(): Flow<List<ExpenseCategory>> = dao.getAllExpenseCategories()
    override fun getAllNotificationHistory(): Flow<List<NotificationHistory>> = dao.getAllNotificationHistory()

    override suspend fun getLatestRates(base: String): ExchangeRateResponse = apiService.getLatestRates(baseCurrency = base)
}