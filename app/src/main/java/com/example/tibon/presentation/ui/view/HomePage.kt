package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.data.local.Account
import com.example.tibon.data.local.Transaction
import com.example.tibon.domain.model.CurrencySetting
import com.example.tibon.presentation.navigation.Routes
import com.example.tibon.presentation.ui.theme.TIBONTheme
import com.example.tibon.presentation.ui.theme.ThemeSetting
import com.example.tibon.presentation.ui.viewmodel.TibonViewModel
import com.example.tibon.presentation.ui.viewmodel.UiState
import com.example.tibon.presentation.util.AppConstants
import com.example.tibon.presentation.util.formatCurrency

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: TibonViewModel = hiltViewModel()
) {
    val accounts by viewModel.allAccounts.collectAsState(initial = emptyList())
    val transactions by viewModel.allTransactions.collectAsState(initial = emptyList())

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

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            HomeHeader(navController)
        }
        item {
            BalanceSummaryCard(
                transactions = transactions,
                currencySetting = currencySetting,
                ratesState = ratesState
            )
        }
        item {
            AccountsHeader(navController = navController)
        }
        items(accounts) { account ->
            val balance by viewModel.getBalanceForAccount(account.id).collectAsState(initial = 0.0)
            val currentBalance = balance ?: 0.0

            val formattedBalance = when (ratesState) {
                is UiState.Success -> {
                    val rates = (ratesState as UiState.Success).data
                    val targetRate = rates[currencySetting.code]
                    formatCurrency(currentBalance, currencySetting, targetRate)
                }
                is UiState.Loading -> "Memuat..."
                is UiState.Error -> formatCurrency(currentBalance, currencySetting, null)
            }

            AccountItem(
                account = account,
                formattedBalance = formattedBalance,
                onCardClick = {
                    navController?.navigate(Routes.detailAccountPage + "/${account.id}")
                },
                onConvertClick = {
                    selectedAccountForConversion = Pair(account, currentBalance)
                    showConversionDialog = true
                }
            )
        }
    }
}

@Composable
fun HomeHeader(navController: NavController?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile Icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.hi_welcome),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(R.string.username),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        IconButton(onClick = { navController?.navigate(Routes.notificationHistoryPage) }) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun BalanceSummaryCard(
    transactions: List<Transaction>,
    currencySetting: CurrencySetting,
    ratesState: UiState<Map<String, Double>>
) {
    val totalIncome = transactions.filter { it.type == "Pemasukan" }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == "Pengeluaran" }.sumOf { it.amount }
    val totalBalance = totalIncome - totalExpense
    val targetRate = if (ratesState is UiState.Success) ratesState.data[currencySetting.code] else null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(R.string.total_saldo),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = formatCurrency(totalBalance, currencySetting, targetRate),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IncomeOutcomeItem(
                    icon = Icons.Default.ArrowUpward,
                    label = "Pemasukan",
                    amount = formatCurrency(totalIncome, currencySetting, targetRate),
                    color = MaterialTheme.colorScheme.primary
                )
                IncomeOutcomeItem(
                    icon = Icons.Default.ArrowDownward,
                    label = "Pengeluaran",
                    amount = formatCurrency(totalExpense, currencySetting, targetRate),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
fun IncomeOutcomeItem(icon: ImageVector, label: String, amount: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
        }
        Spacer(Modifier.width(8.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = amount, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun AccountsHeader(navController: NavController?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.daftar_rekening),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        TextButton(onClick = { navController?.navigate(Routes.allAccountsPage) }) {
            Text(text = "Lihat Semua")
        }
    }
}

@Composable
fun AccountItem(
    account: Account,
    formattedBalance: String,
    onCardClick: () -> Unit,
    onConvertClick: () -> Unit
) {
    Card(
        onClick = onCardClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconRes = if (account.type == "Tabungan") {
                R.drawable.piggybank
            } else {
                R.drawable.transaction
            }

            Image(
                painter = painterResource(id = iconRes),
                contentDescription = account.name,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .padding(8.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = account.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = account.notes ?: "Rekening",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = formattedBalance,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                IconButton(
                    onClick = onConvertClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Konversi Mata Uang",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun CurrencyConversionDialog(
    accountName: String,
    baseBalance: Double,
    baseCurrencySetting: CurrencySetting,
    rates: Map<String, Double>,
    allAvailableCurrencies: List<CurrencySetting>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Konversi Saldo: $accountName") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Saldo Asli: ${formatCurrency(baseBalance, baseCurrencySetting, rates[baseCurrencySetting.code])}",
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                allAvailableCurrencies.forEach { targetCurrency ->
                    if (targetCurrency.code != baseCurrencySetting.code) {
                        val targetRate = rates[targetCurrency.code]
                        val formatted = formatCurrency(baseBalance, targetCurrency, targetRate)
                        Text("${targetCurrency.code}: $formatted")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Tutup")
            }
        }
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun HomePagePreviewLight() {
    TIBONTheme(themeSetting = ThemeSetting.Light) {
        HomePage()
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun HomePagePreviewDark() {
    TIBONTheme(themeSetting = ThemeSetting.Dark) {
        HomePage()
    }
}