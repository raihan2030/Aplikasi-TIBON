package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.presentation.ui.theme.TIBONTheme
import com.example.tibon.presentation.ui.theme.ThemeSetting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    navController: NavController? = null,
    currentTheme: ThemeSetting,
    onThemeChange: (ThemeSetting) -> Unit = {} // Terima lambda
) {
    var showThemeDialog by remember { mutableStateOf(false) }

    if (showThemeDialog) {
        ThemeSelectionDialog(
            currentTheme = currentTheme,
            onDismiss = { showThemeDialog = false },
            onThemeSelected = { newTheme ->
                onThemeChange(newTheme)
                showThemeDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.pengaturan), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            SettingsItem(
                icon = Icons.Default.Brush,
                title = stringResource(R.string.mode_tema),
                subtitle = "Pilih tema aplikasi", // Ganti dengan deskripsi yang lebih baik
                onClick = { showThemeDialog = true }
            )
            SettingsItem(
                icon = Icons.Default.Language,
                title = stringResource(R.string.bahasa),
                subtitle = "Pilih bahasa aplikasi",
                onClick = { /* TODO */ }
            )
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ThemeSelectionDialog(
    currentTheme: ThemeSetting,
    onDismiss: () -> Unit,
    onThemeSelected: (ThemeSetting) -> Unit
) {
    val themeOptions = listOf(
        ThemeSetting.System to "Default Sistem",
        ThemeSetting.Light to "Terang",
        ThemeSetting.Dark to "Gelap"
    )
    var selectedOption by remember { mutableStateOf(currentTheme) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Pilih Tema", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))
                themeOptions.forEach { (theme, label) ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = theme }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (theme == selectedOption),
                            onClick = { selectedOption = theme }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = label)
                    }
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Batal")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onThemeSelected(selectedOption) }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingsPagePreviewLight() {
    TIBONTheme(themeSetting = ThemeSetting.Light) {
        SettingsPage(currentTheme = ThemeSetting.Light, onThemeChange = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPagePreviewDark() {
    TIBONTheme(themeSetting = ThemeSetting.Dark) {
        SettingsPage(currentTheme = ThemeSetting.Dark, onThemeChange = {})
    }
}