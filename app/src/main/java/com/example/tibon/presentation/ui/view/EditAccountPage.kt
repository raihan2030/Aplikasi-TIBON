package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.presentation.ui.viewmodel.TibonViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccountPage(
    navController: NavController,
    accountId: Int,
    viewModel: TibonViewModel = hiltViewModel()
) {
    val account by viewModel.getAccountById(accountId).collectAsState(initial = null)

    var nameText by remember { mutableStateOf("") }
    var notesText by remember { mutableStateOf("") }

    val scheduleOptions = listOf("Tidak Aktif", "Setiap Hari", "Hari Tertentu")
    var selectedSchedule by remember { mutableStateOf(scheduleOptions[0]) }
    val days = listOf("SENIN", "SELASA", "RABU", "KAMIS", "JUMAT", "SABTU", "MINGGU")
    val selectedDays = remember { mutableStateListOf<String>() }

    var selectedHour by remember { mutableIntStateOf(8) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(account) {
        account?.let {
            nameText = it.name
            notesText = it.notes ?: ""
            selectedHour = it.notificationHour
            selectedMinute = it.notificationMinute

            when {
                it.notificationSchedule == "NONE" -> {
                    selectedSchedule = "Tidak Aktif"
                    selectedDays.clear()
                }
                it.notificationSchedule == "DAILY" -> {
                    selectedSchedule = "Setiap Hari"
                    selectedDays.clear()
                }
                it.notificationSchedule.isNotBlank() -> {
                    selectedSchedule = "Hari Tertentu"
                    selectedDays.clear()
                    selectedDays.addAll(it.notificationSchedule.split(','))
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_rekening), fontWeight = FontWeight.Bold) },
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
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        enabled = account != null,
                        onClick = {
                            account?.let {
                                val newSchedule = when (selectedSchedule) {
                                    "Setiap Hari" -> "DAILY"
                                    "Hari Tertentu" -> if (selectedDays.isEmpty()) "NONE" else selectedDays.joinToString(",")
                                    else -> "NONE"
                                }

                                val updatedAccount = it.copy(
                                    name = nameText,
                                    notes = notesText.ifEmpty { null },
                                    notificationSchedule = newSchedule,
                                    notificationHour = selectedHour,
                                    notificationMinute = selectedMinute
                                )
                                viewModel.updateAccount(updatedAccount)
                                navController.popBackStack()
                            }
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
        if (account == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
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
                TextInput(
                    text = notesText,
                    onTextChange = { notesText = it },
                    placeholder = "Contoh: Untuk dana darurat",
                    title = stringResource(R.string.catatan)
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Pengaturan Notifikasi",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                var expandedDropdown by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expandedDropdown,
                    onExpandedChange = { expandedDropdown = it }
                ) {
                    OutlinedTextField(
                        value = selectedSchedule,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Frekuensi Pengingat") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedDropdown,
                        onDismissRequest = { expandedDropdown = false }
                    ) {
                        scheduleOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedSchedule = option
                                    expandedDropdown = false
                                }
                            )
                        }
                    }
                }

                if (selectedSchedule != "Tidak Aktif") {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Waktu Pengingat",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showTimePicker = true }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = String.format("%02d:%02d", selectedHour, selectedMinute),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Ubah Waktu"
                        )
                    }
                }

                if (selectedSchedule == "Hari Tertentu") {
                    Spacer(Modifier.height(8.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        days.forEach { day ->
                            val isSelected = selectedDays.contains(day)
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    if (isSelected) {
                                        selectedDays.remove(day)
                                    } else {
                                        selectedDays.add(day)
                                    }
                                },
                                label = { Text(day.replaceFirstChar { it.titlecase(Locale.getDefault()) }) }
                            )
                        }
                    }
                }
            }
        }
    }

    // [DIPERBAIKI] Implementasi TimePickerDialog yang benar untuk Compose
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = selectedHour,
            initialMinute = selectedMinute,
            is24Hour = true
        )
        Dialog(onDismissRequest = { showTimePicker = false }) {
            Surface(shape = MaterialTheme.shapes.extraLarge) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimePicker(state = timePickerState)
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showTimePicker = false }) {
                            Text("Batal")
                        }
                        Spacer(Modifier.width(8.dp))
                        TextButton(
                            onClick = {
                                selectedHour = timePickerState.hour
                                selectedMinute = timePickerState.minute
                                showTimePicker = false
                            }
                        ) { Text("OK") }
                    }
                }
            }
        }
    }
}