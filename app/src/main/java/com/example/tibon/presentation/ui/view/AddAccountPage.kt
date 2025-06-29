package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.presentation.ui.theme.TIBONTheme
import com.example.tibon.presentation.ui.theme.ThemeSetting
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tibon.presentation.ui.viewmodel.TibonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountPage(
    navController: NavController? = null,
    viewModel: TibonViewModel = hiltViewModel()
) {
    var nameText by rememberSaveable { mutableStateOf("") }
    var initialBalanceText by rememberSaveable { mutableStateOf("") }
    var notesText by rememberSaveable { mutableStateOf("") }

    val accountTypes = listOf("Transaksi", "Tabungan")
    var expanded by remember { mutableStateOf(false) }
    var selectedAccountType by rememberSaveable { mutableStateOf(accountTypes[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tambah_rekening), fontWeight = FontWeight.Bold) },
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
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        onClick = {
                            val balance = initialBalanceText.toDoubleOrNull() ?: 0.0
                            viewModel.addAccount(
                                nameText,
                                balance,
                                notesText.ifEmpty { null },
                                selectedAccountType
                            )
                            navController?.popBackStack()
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
            TextInput(
                text = nameText,
                onTextChange = { nameText = it },
                placeholder = "Contoh: Tabungan Utama",
                title = "Nama Rekening"
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Tipe Rekening",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedAccountType,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        accountTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(text = type) },
                                onClick = {
                                    selectedAccountType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            NumberInput(
                text = initialBalanceText,
                onTextChange = { initialBalanceText = it },
                placeholder = "0",
                title = "Saldo Awal (Opsional)"
            )
            TextInput(
                text = notesText,
                onTextChange = { notesText = it },
                placeholder = "Contoh: Untuk dana darurat",
                title = "Catatan (Opsional)"
            )
        }
    }
}

@Composable
fun NumberInput(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    title: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )
        OutlinedTextField(
            value = text,
            onValueChange = { onTextChange(it.filter { char -> char.isDigit() }) },
            placeholder = { Text(text = placeholder) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Text("Rp") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            )
        )
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
fun AddAccountPreviewLight() {
    TIBONTheme(themeSetting = ThemeSetting.Light) {
        AddAccountPage()
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun AddAccountPreviewDark() {
    TIBONTheme(themeSetting = ThemeSetting.Dark) {
        AddAccountPage()
    }
}