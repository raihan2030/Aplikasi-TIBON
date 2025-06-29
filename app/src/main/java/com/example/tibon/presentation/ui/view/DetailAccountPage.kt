package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.tibon.presentation.util.formatCurrency
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAccountPage(
    navController: NavController,
    accountId: Int,
    viewModel: TibonViewModel = hiltViewModel()
) {
    val account by viewModel.getAccountById(accountId).collectAsState(initial = null)
    val balance by viewModel.getBalanceForAccount(accountId).collectAsState(initial = null)
    val transactions by viewModel.getTransactionsForAccount(accountId).collectAsState(initial = emptyList())

    val currencySetting by viewModel.currencySetting.collectAsState()
    val ratesState by viewModel.conversionRates.collectAsState()

    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Rekening?") },
            text = { Text("Apakah Anda yakin ingin menghapus rekening '${account?.name}'? Semua transaksi terkait juga akan dihapus.") },
            confirmButton = {
                Button(
                    onClick = {
                        account?.let {
                            viewModel.deleteAccount(it)
                            navController.popBackStack()
                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.detail_rekening), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More Options")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.edit_rekening)) },
                            onClick = {
                                showMenu = false
                                navController.navigate(Routes.editAccountPage + "/$accountId")
                            },
                            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.hapus), color = MaterialTheme.colorScheme.error) },
                            onClick = {
                                showMenu = false
                                showDeleteDialog = true
                            },
                            leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error) }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            account?.let {
                BottomAppBar(
                    navController = navController,
                    accountId = accountId,
                    accountType = it.type
                )
            }
        }
    ) { innerPadding ->
        account?.let { acc ->
            val totalBalance = balance ?: 0.0
            val totalIncome = transactions.filter { it.type == "Pemasukan" }.sumOf { it.amount }
            val totalExpense = transactions.filter { it.type == "Pengeluaran" }.sumOf { it.amount }

            val targetRate = if (ratesState is UiState.Success) (ratesState as UiState.Success).data[currencySetting.code] else null

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                item {
                    AccountDetailHeader(
                        account = acc,
                        formattedTotalBalance = formatCurrency(totalBalance, currencySetting, targetRate)
                    )
                }

                item { Spacer(Modifier.height(24.dp)) }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        // Placeholder for chart
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            painter = painterResource(R.drawable.line_chart),
                            contentDescription = "Grafik Transaksi"
                        )
                    }
                }

                if (acc.type == "Transaksi") {
                    item { Spacer(Modifier.height(24.dp)) }

                    item {
                        FinancialDetails(
                            formattedTotalIncome = formatCurrency(totalIncome, currencySetting, targetRate),
                            formattedTotalExpense = formatCurrency(totalExpense, currencySetting, targetRate)
                        )
                    }
                }

                item { Spacer(Modifier.height(24.dp)) }

                if (!acc.notes.isNullOrEmpty()) {
                    item { NotesSection(notes = acc.notes) }
                }

                item { Spacer(Modifier.height(16.dp)) }

                if (transactions.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Riwayat Terbaru",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            TextButton(onClick = {
                                navController.navigate(Routes.accountHistoryPage + "/$accountId")
                            }) {
                                Text("Lihat Semua")
                            }
                        }
                    }
                    items(transactions.take(5)) { transaction -> // Show latest 5
                        TransactionHistoryItem(
                            transaction = transaction,
                            currencySetting = currencySetting,
                            targetRate = targetRate
                        )
                    }
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun AccountDetailHeader(account: Account, formattedTotalBalance: String) {
    Text(
        text = account.name,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = "Total Saldo: $formattedTotalBalance",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun FinancialDetails(formattedTotalIncome: String, formattedTotalExpense: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Detail Keuangan",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DetailRow("Total Pemasukan", formattedTotalIncome)
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        DetailRow("Total Pengeluaran", formattedTotalExpense)
    }
}

@Composable
fun NotesSection(notes: String) {
    Text(
        "Catatan",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = notes,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun BottomAppBar(navController: NavController, accountId: Int, accountType: String) {
    Box(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
        Surface(shadowElevation = 8.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (accountType == "Transaksi") {
                    Button(
                        onClick = { navController.navigate(Routes.addTransactionPage + "/$accountId/Pengeluaran") },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Pengeluaran")
                        Spacer(Modifier.width(8.dp))
                        Text("Pengeluaran")
                    }
                }

                Button(
                    onClick = { navController.navigate(Routes.addTransactionPage + "/$accountId/Pemasukan") },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Pemasukan")
                    Spacer(Modifier.width(8.dp))
                    Text("Pemasukan")
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TransactionHistoryItem(
    transaction: Transaction,
    currencySetting: CurrencySetting,
    targetRate: Double?
) {
    val isIncome = transaction.type == "Pemasukan"
    val amountColor = if (isIncome) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
    val icon = if (isIncome) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward
    val formatter = remember { SimpleDateFormat("dd MMM yyyy", Locale("id", "ID")) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = transaction.type, tint = amountColor)
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(transaction.category, fontWeight = FontWeight.SemiBold)
                if (!transaction.description.isNullOrBlank()) {
                    Text(
                        text = transaction.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = formatCurrency(transaction.amount, currencySetting, targetRate),
                    fontWeight = FontWeight.Bold,
                    color = amountColor
                )
                Text(
                    text = formatter.format(transaction.date),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun DetailAccountPreviewLight() {
    TIBONTheme(themeSetting = ThemeSetting.Light) {
        // Preview requires a NavController and accountId, so it might not render correctly.
        // DetailAccountPage(navController = rememberNavController(), accountId = 1)
    }
}