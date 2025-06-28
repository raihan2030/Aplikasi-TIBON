package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.presentation.ui.theme.TIBONTheme
import com.example.tibon.presentation.ui.theme.ThemeSetting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionPage(
    navController: NavController? = null,
    transactionType: String? // "pemasukan" atau "pengeluaran"
) {
    val pageTitle = if (transactionType == "pemasukan") "Tambah Pemasukan" else "Tambah Pengeluaran"

    // --- State untuk data input ---
    var amountText by rememberSaveable { mutableStateOf("") }
    var descriptionText by rememberSaveable { mutableStateOf("") }
    var dateText by rememberSaveable { mutableStateOf("") }

    // --- State untuk dropdown kategori ---
    val incomeCategories = listOf("Gaji", "Bonus", "Hadiah", "Lainnya")
    val expenseCategories = listOf("Makanan", "Transportasi", "Belanja", "Hiburan", "Tagihan", "Lainnya")
    val categories = if (transactionType == "pemasukan") incomeCategories else expenseCategories

    var categoryExpanded by remember { mutableStateOf(false) }
    var selectedCategory by rememberSaveable { mutableStateOf(categories[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pageTitle, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Box(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
                Surface(shadowElevation = 8.dp) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(55.dp),
                        onClick = {
                            // TODO: Logika untuk menyimpan transaksi
                            navController?.navigateUp()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.simpan),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NumberInput(
                text = amountText,
                onTextChange = { amountText = it },
                placeholder = "0",
                title = "Jumlah"
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Kategori",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = !categoryExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(text = category) },
                                onClick = {
                                    selectedCategory = category
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            TextInput(
                text = descriptionText,
                onTextChange = { descriptionText = it },
                placeholder = "Contoh: Gaji bulan ini",
                title = "Deskripsi"
            )
            DateInput(
                text = dateText,
                onTextChange = { dateText = it },
                placeholder = "Pilih tanggal",
                title = "Tanggal Transaksi"
            )
        }
    }
}

@Preview(showBackground = true, name = "Tambah Pemasukan")
@Composable
fun AddIncomePreview() {
    TIBONTheme(themeSetting = ThemeSetting.Light) {
        AddTransactionPage(transactionType = "pemasukan")
    }
}

@Preview(showBackground = true, name = "Tambah Pengeluaran")
@Composable
fun AddExpensePreview() {
    TIBONTheme(themeSetting = ThemeSetting.Dark) {
        AddTransactionPage(transactionType = "pengeluaran")
    }
}