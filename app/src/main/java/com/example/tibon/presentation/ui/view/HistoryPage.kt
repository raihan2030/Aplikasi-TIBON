package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.data.local.Transaction
import com.example.tibon.domain.model.CurrencySetting
import com.example.tibon.presentation.ui.theme.TIBONTheme
import com.example.tibon.presentation.ui.theme.ThemeSetting
import com.example.tibon.presentation.ui.viewmodel.TibonViewModel
import com.example.tibon.presentation.ui.viewmodel.UiState
import com.example.tibon.presentation.util.formatCurrency
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: TibonViewModel = hiltViewModel()
) {
    val accounts by viewModel.allAccounts.collectAsState(initial = emptyList())
    val transactions by viewModel.allTransactions.collectAsState(initial = emptyList())
    var searchText by rememberSaveable { mutableStateOf("") }

    val currencySetting by viewModel.currencySetting.collectAsState()
    val ratesState by viewModel.conversionRates.collectAsState()

    val transactionWithAccountName = transactions.map { transaction ->
        val accountName = accounts.find { it.id == transaction.accountId }?.name ?: "Tidak Diketahui"
        Pair(transaction, accountName)
    }

    val filteredTransactions = if (searchText.isBlank()) {
        transactionWithAccountName
    } else {
        transactionWithAccountName.filter { (transaction, accountName) ->
            transaction.category.contains(searchText, ignoreCase = true) || accountName.contains(searchText, ignoreCase = true)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            stringResource(R.string.transaction_history),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))

        //Search Bar
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text(text = stringResource(R.string.cari_transaksi)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )

        // History List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredTransactions) { (transaction, accountName) ->
                HistoryItem(
                    transaction = transaction,
                    accountName = accountName,
                    currencySetting = currencySetting,
                    ratesState = ratesState
                )
            }
        }
    }
}

@Composable
fun HistoryItem(
    transaction: Transaction,
    accountName: String,
    currencySetting: CurrencySetting,
    ratesState: UiState<Map<String, Double>>
) {
    val isIncome = transaction.type == "Pemasukan"
    val amountColor = if (isIncome) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
    val icon = if (isIncome) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward
    val formatter = remember { SimpleDateFormat("dd MMM yy", Locale("id", "ID")) }
    val targetRate = if (ratesState is UiState.Success) ratesState.data[currencySetting.code] else null
    val formattedAmount = (if (isIncome) "+" else "-") + formatCurrency(transaction.amount, currencySetting, targetRate)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(amountColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = transaction.type,
                    tint = amountColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.category,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = accountName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = formattedAmount,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = amountColor
                )
                Text(
                    text = formatter.format(transaction.date),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun HistoryPagePreviewLight() {
    TIBONTheme(themeSetting = ThemeSetting.Light) {
        HistoryPage()
    }
}