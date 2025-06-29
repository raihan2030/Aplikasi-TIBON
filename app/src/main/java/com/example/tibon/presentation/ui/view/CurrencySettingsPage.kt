package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tibon.presentation.ui.viewmodel.TibonViewModel
import com.example.tibon.presentation.util.AppConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySettingsPage(
    navController: NavController,
    viewModel: TibonViewModel = hiltViewModel()
) {
    val popularCurrencies = AppConstants.POPULAR_CURRENCIES

    val currentSetting by viewModel.currencySetting.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pilih Mata Uang", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(popularCurrencies) { (code, symbol) ->
                CurrencyItem(
                    code = code,
                    symbol = symbol,
                    isSelected = currentSetting.code == code,
                    onClick = {
                        viewModel.changeCurrency(code, symbol)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
fun CurrencyItem(
    code: String,
    symbol: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$symbol ($code)",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}