package com.example.tibon.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Account Queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account): Long

    @Update
    suspend fun updateAccount(account: Account)

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("SELECT * FROM accounts ORDER BY name ASC")
    fun getAllAccounts(): Flow<List<Account>>

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getAccountById(accountId: Int): Flow<Account>

    // Transaction Queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE accountId = :accountId ORDER BY date DESC")
    fun getTransactionsForAccount(accountId: Int): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT SUM(CASE WHEN type = 'Pemasukan' THEN amount ELSE -amount END) FROM transactions WHERE accountId = :accountId")
    fun getBalanceForAccount(accountId: Int): Flow<Double?>

    // Income Categories
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIncomeCategory(category: IncomeCategory)

    @Query("SELECT * FROM income_categories ORDER BY name ASC")
    fun getAllIncomeCategories(): Flow<List<IncomeCategory>>

    // Expense Categories
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExpenseCategory(category: ExpenseCategory)

    @Query("SELECT * FROM expense_categories ORDER BY name ASC")
    fun getAllExpenseCategories(): Flow<List<ExpenseCategory>>

    // NotificationHistory
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotificationHistory(notification: NotificationHistory)

    @Query("SELECT * FROM notification_history ORDER BY timestamp DESC")
    fun getAllNotificationHistory(): Flow<List<NotificationHistory>>
}