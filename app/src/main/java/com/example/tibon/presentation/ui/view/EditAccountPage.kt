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
import com.example.tibon.presentation.navigation.Routes
import com.example.tibon.presentation.ui.theme.TIBONTheme
import com.example.tibon.presentation.ui.theme.ThemeSetting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccountPage(navController: NavController? = null) {
    // Di aplikasi nyata, nilai-nilai ini akan diambil dari ViewModel/database
    var nameText by rememberSaveable { mutableStateOf("Tabunganku") }
    var initialBalanceText by rememberSaveable { mutableStateOf("30000") }
    var notesText by rememberSaveable { mutableStateOf("Ini adalah rekening yang digunakan untuk dana darurat.") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_rekening), fontWeight = FontWeight.Bold) },
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
                            // Di sini logika untuk menyimpan perubahan ke database
                            navController?.navigateUp() // Kembali ke halaman detail setelah menyimpan
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.simpan_perubahan),
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
            // Menggunakan kembali Composable TextInput
            TextInput(
                text = nameText,
                onTextChange = { nameText = it },
                placeholder = "Contoh: Tabungan Utama",
                title = "Nama Rekening"
            )
            TextInput(
                text = notesText,
                onTextChange = { notesText = it },
                placeholder = "Contoh: Untuk dana darurat",
                title = stringResource(R.string.catatan)
            )
        }
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
fun EditAccountPreviewLight() {
    TIBONTheme(themeSetting = ThemeSetting.Light) {
        EditAccountPage()
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun EditAccountPreviewDark() {
    TIBONTheme(themeSetting = ThemeSetting.Dark) {
        EditAccountPage()
    }
}