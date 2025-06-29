package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tibon.presentation.ui.viewmodel.TibonViewModel
import com.example.tibon.presentation.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountHistoryPage(
    navController: NavController,
    accountId: Int,
    viewModel: TibonViewModel = hiltViewModel()
) {
    val account by viewModel.getAccountById(accountId).collectAsState(initial = null)
    val transactions by viewModel.getTransactionsForAccount(accountId).collectAsState(initial = emptyList())

    val currencySetting by viewModel.currencySetting.collectAsState()
    val ratesState by viewModel.conversionRates.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = account?.name ?: "Riwayat Transaksi",
                        fontWeight = FontWeight.Bold
                    )
                },
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(transactions) { transaction ->
                val targetRate = if (ratesState is UiState.Success) {
                    (ratesState as UiState.Success).data[currencySetting.code]
                } else {
                    null
                }

                TransactionHistoryItem(
                    transaction = transaction,
                    currencySetting = currencySetting,
                    targetRate = targetRate
                )
            }
        }
    }
}