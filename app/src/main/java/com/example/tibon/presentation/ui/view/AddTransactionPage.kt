package com.example.tibon.presentation.ui.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.presentation.ui.viewmodel.TibonViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionPage(
    navController: NavController,
    accountId: Int,
    transactionType: String,
    viewModel: TibonViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val pageTitle = if (transactionType == "Pemasukan") "Tambah Pemasukan" else "Tambah Pengeluaran"

    var amountText by rememberSaveable { mutableStateOf("") }
    var descriptionText by rememberSaveable { mutableStateOf("") }

    val today = remember { Date() }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    var selectedDate by remember { mutableStateOf(today) }
    var dateText by rememberSaveable { mutableStateOf(dateFormatter.format(today)) }

    val incomeCategories by viewModel.allIncomeCategories.collectAsState(initial = emptyList())
    val expenseCategories by viewModel.allExpenseCategories.collectAsState(initial = emptyList())
    val categories = remember(incomeCategories, expenseCategories, transactionType) {
        val sourceList = if (transactionType == "Pemasukan") {
            incomeCategories.map { it.name }
        } else {
            expenseCategories.map { it.name }
        }
        val others = sourceList.filter { it.equals("Lainnya", ignoreCase = true) }
        val sortedList = sourceList.filter { !it.equals("Lainnya", ignoreCase = true) }.sorted()
        sortedList + others
    }

    var categoryExpanded by remember { mutableStateOf(false) }
    var selectedCategory by rememberSaveable { mutableStateOf(categories.firstOrNull() ?: "") }

    LaunchedEffect(categories) {
        if (categories.isNotEmpty()) {
            selectedCategory = categories.first()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pageTitle, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
                            val amount = amountText.toDoubleOrNull()
                            if (amount == null || amount <= 0) {
                                Toast.makeText(context, "Jumlah harus diisi dan lebih dari 0", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            viewModel.addTransaction(
                                accountId = accountId,
                                amount = amount,
                                type = transactionType,
                                category = selectedCategory,
                                description = descriptionText,
                                date = selectedDate
                            )
                            navController.popBackStack()
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
                        if (categories.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text(text = "Memuat...") },
                                onClick = { /* Do nothing */ }
                            )
                        } else {
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
            }
            TextInput(
                text = descriptionText,
                onTextChange = { descriptionText = it },
                placeholder = "Contoh: Makan siang bareng teman",
                title = "Deskripsi (Opsional)"
            )
            DateInput(
                text = dateText,
                onDateSelected = { dateString, dateObject ->
                    dateText = dateString
                    selectedDate = dateObject
                },
                placeholder = "Pilih tanggal",
                title = "Tanggal Transaksi"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInput(
    text: String,
    onDateSelected: (String, Date) -> Unit,
    placeholder: String,
    title: String
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val selectedDateMillis = remember(text) {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.parse(text)?.time
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Calendar.getInstance().apply {
                                timeInMillis = millis
                            }.time
                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            onDateSelected(sdf.format(selectedDate), selectedDate)
                        }
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )
        Box {
            OutlinedTextField(
                value = text,
                onValueChange = { },
                readOnly = true,
                placeholder = { Text(text = placeholder) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Calendar Icon")
                }
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Transparent)
                    .clickable { showDatePicker = true }
            )
        }
    }
}