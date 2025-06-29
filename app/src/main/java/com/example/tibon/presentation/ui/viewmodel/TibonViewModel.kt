package com.example.tibon.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tibon.data.local.Account
import com.example.tibon.data.local.Transaction
import com.example.tibon.domain.model.CurrencySetting
import com.example.tibon.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

@HiltViewModel
class TibonViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val addAccountUseCase: AddAccountUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getTransactionsForAccountUseCase: GetTransactionsForAccountUseCase,
    private val getBalanceForAccountUseCase: GetBalanceForAccountUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getIncomeCategoriesUseCase: GetIncomeCategoriesUseCase,
    private val getExpenseCategoriesUseCase: GetExpenseCategoriesUseCase,
    private val getCurrencySettingUseCase: GetCurrencySettingUseCase,
    private val saveCurrencySettingUseCase: SaveCurrencySettingUseCase,
    private val fetchRatesUseCase: FetchRatesUseCase,
    private val getNotificationHistoryUseCase: GetNotificationHistoryUseCase,
//    private val signUpUseCase: SignUpUseCase,
//    private val loginUseCase: LoginUseCase,
//    private val getCurrentUserUseCase: GetCurrentUserUseCase,
//    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    val allAccounts = getAccountsUseCase()
    val allTransactions = getAllTransactionsUseCase()
    val allIncomeCategories = getIncomeCategoriesUseCase()
    val allExpenseCategories = getExpenseCategoriesUseCase()

    private val _currencySetting = MutableStateFlow(CurrencySetting("IDR", "Rp"))
    val currencySetting = _currencySetting.asStateFlow()

    private val _conversionRates = MutableStateFlow<UiState<Map<String, Double>>>(UiState.Loading)
    val conversionRates = _conversionRates.asStateFlow()

    val notificationHistory = getNotificationHistoryUseCase()

    init {
        viewModelScope.launch {
            getCurrencySettingUseCase().collect { setting ->
                _currencySetting.value = setting
                fetchRates(setting.code)
            }
        }
    }

    fun changeCurrency(code: String, symbol: String) {
        viewModelScope.launch {
            saveCurrencySettingUseCase(code, symbol)
        }
    }

    private fun fetchRates(baseCurrency: String) {
        viewModelScope.launch {
            fetchRatesUseCase(baseCurrency).collect { result ->
                _conversionRates.value = result
            }
        }
    }

    fun getAccountById(accountId: Int): Flow<Account?> {
        return getAccountByIdUseCase(accountId)
    }

    fun getTransactionsForAccount(accountId: Int): Flow<List<Transaction>> {
        return getTransactionsForAccountUseCase(accountId)
    }

    fun getBalanceForAccount(accountId: Int): Flow<Double?> {
        return getBalanceForAccountUseCase(accountId)
    }

    fun addAccount(name: String, initialBalance: Double, notes: String?, type: String) {
        viewModelScope.launch {
            addAccountUseCase(name, initialBalance, notes, type)
        }
    }

    fun updateAccount(account: Account) {
        viewModelScope.launch {
            updateAccountUseCase(account)
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            deleteAccountUseCase(account)
        }
    }

    fun addTransaction(
        accountId: Int,
        amount: Double,
        type: String,
        category: String,
        description: String?,
        date: Date
    ) {
        viewModelScope.launch {
            addTransactionUseCase(accountId, amount, type, category, description, date)
        }
    }

    // Fungsi deleteTransaction jika diperlukan di masa depan
    // fun deleteTransaction(transaction: Transaction) { ... }
}