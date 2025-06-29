package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tibon.data.local.Account
import com.example.tibon.presentation.navigation.Routes
import com.example.tibon.presentation.ui.viewmodel.TibonViewModel
import com.example.tibon.presentation.ui.viewmodel.UiState
import com.example.tibon.presentation.util.AppConstants
import com.example.tibon.presentation.util.formatCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllAccountsPage(
    navController: NavController,
    viewModel: TibonViewModel = hiltViewModel()
) {
    val accounts by viewModel.allAccounts.collectAsState(initial = emptyList())
    var selectedFilter by remember { mutableStateOf("Semua") }

    val currencySetting by viewModel.currencySetting.collectAsState()
    val ratesState by viewModel.conversionRates.collectAsState()

    var showConversionDialog by remember { mutableStateOf(false) }
    var selectedAccountForConversion by remember { mutableStateOf<Pair<Account, Double>?>(null) }

    if (showConversionDialog && selectedAccountForConversion != null && ratesState is UiState.Success) {
        CurrencyConversionDialog(
            accountName = selectedAccountForConversion!!.first.name,
            baseBalance = selectedAccountForConversion!!.second,
            baseCurrencySetting = currencySetting,
            rates = (ratesState as UiState.Success).data,
            allAvailableCurrencies = AppConstants.POPULAR_CURRENCIES,
            onDismiss = { showConversionDialog = false }
        )
    }

    val filteredAccounts = when (selectedFilter) {
        "Transaksi" -> accounts.filter { it.type == "Transaksi" }
        "Tabungan" -> accounts.filter { it.type == "Tabungan" }
        else -> accounts
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Semua Rekening", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            // Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                FilterChipComponent(
                    label = "Semua",
                    isSelected = selectedFilter == "Semua",
                    onClick = { selectedFilter = "Semua" }
                )
                Spacer(Modifier.width(8.dp))
                FilterChipComponent(
                    label = "Transaksi",
                    isSelected = selectedFilter == "Transaksi",
                    onClick = { selectedFilter = "Transaksi" }
                )
                Spacer(Modifier.width(8.dp))
                FilterChipComponent(
                    label = "Tabungan",
                    isSelected = selectedFilter == "Tabungan",
                    onClick = { selectedFilter = "Tabungan" }
                )
            }

            // Account List
            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(filteredAccounts) { account ->
                    val balance by viewModel.getBalanceForAccount(account.id).collectAsState(initial = 0.0)
                    val currentBalance = balance ?: 0.0

                    val formattedBalance = when(ratesState) {
                        is UiState.Success -> {
                            val rates = (ratesState as UiState.Success).data
                            val targetRate = rates[currencySetting.code]
                            formatCurrency(balance ?: 0.0, currencySetting, targetRate)
                        }
                        is UiState.Loading -> "Memuat..."
                        is UiState.Error -> formatCurrency(balance ?: 0.0, currencySetting, null)
                    }

                    AccountItem(
                        account = account,
                        formattedBalance = formattedBalance,
                        onCardClick = {
                            navController.navigate(Routes.detailAccountPage + "/${account.id}")
                        },
                        onConvertClick = {
                            selectedAccountForConversion = Pair(account, currentBalance)
                            showConversionDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FilterChipComponent(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(label) },
        leadingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        }
    )
}