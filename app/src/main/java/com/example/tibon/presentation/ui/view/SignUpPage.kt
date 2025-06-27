package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.presentation.navigation.Routes
import com.example.tibon.presentation.ui.theme.TIBONTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun SignUpPage(navController: NavController? = null) {
    var fullNameText by rememberSaveable { mutableStateOf("") }
    var emailText by rememberSaveable { mutableStateOf("") }
    var mobileNumText by rememberSaveable { mutableStateOf("") }
    var dateOfBirthText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    var confPasswordText by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            // Box untuk Header "Create Account"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f), // Mengambil 1/4 bagian atas layar
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.create_account),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = FontFamily(Font(R.font.comfortaa_bold)),
                    fontSize = 30.sp
                )
            }

            // Column untuk Form, ditumpuk di atas dan diatur ke bawah
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f) // Mengambil sisa layar dengan sedikit overlap
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                    .padding(start = 32.dp, end = 32.dp, top = 24.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(16.dp))

                TextInput(
                    text = fullNameText,
                    onTextChange = { fullNameText = it },
                    placeholder = stringResource(R.string.full_name_placeholder),
                    title = stringResource(R.string.full_name)
                )
                Spacer(Modifier.height(16.dp))

                TextInput(
                    text = emailText,
                    onTextChange = { emailText = it },
                    placeholder = stringResource(R.string.email_placeholder),
                    title = stringResource(R.string.email)
                )
                Spacer(Modifier.height(16.dp))

                PhoneNumberInput(
                    text = mobileNumText,
                    onTextChange = { mobileNumText = it },
                    placeholder = stringResource(id = R.string.mobile_num_placeholder),
                    title = stringResource(R.string.mobile_number)
                )
                Spacer(Modifier.height(16.dp))

                DateInput(
                    text = dateOfBirthText,
                    onTextChange = { dateOfBirthText = it },
                    placeholder = stringResource(id = R.string.date_placeholder),
                    title = stringResource(R.string.date_of_birth)
                )
                Spacer(Modifier.height(16.dp))

                PasswordInput(
                    passwordText = passwordText,
                    onPasswordChange = { passwordText = it },
                    passwordVisible = passwordVisible,
                    onVisibilityChange = { passwordVisible = !passwordVisible },
                    title = stringResource(R.string.password),
                    placeholder = stringResource(R.string.password_placeholder)
                )
                Spacer(Modifier.height(16.dp))

                PasswordInput(
                    passwordText = confPasswordText,
                    onPasswordChange = { confPasswordText = it },
                    passwordVisible = confPasswordVisible,
                    onVisibilityChange = { confPasswordVisible = !confPasswordVisible },
                    title = stringResource(R.string.confirm_password),
                    placeholder = stringResource(R.string.conf_pass_placeholder)
                )

                Spacer(Modifier.height(32.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                        navController?.navigate(Routes.loginPage)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.signup),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                TextButton(
                    onClick = {
                        navController?.navigate(Routes.loginPage)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.already_have_account),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

// Composable baru untuk input nomor telepon
@Composable
fun PhoneNumberInput(
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
            onValueChange = { onTextChange(it.filter { char -> char.isDigit() }) }, // Hanya mengizinkan angka
            placeholder = { Text(text = placeholder) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone Icon")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            )
        )
    }
}

// Composable baru untuk input tanggal lahir (versi bersih)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInput(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    title: String
) {
    // Variabel yang tidak digunakan telah dihapus
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        // Pastikan tanggal dipilih sebelum memformat
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Calendar.getInstance().apply {
                                timeInMillis = millis
                            }
                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            onTextChange(sdf.format(selectedDate.time))
                        }
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) { Text("Cancel") }
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
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Calendar Icon")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                )
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { showDatePicker = true }
            )
        }
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
fun SignUpPagePreviewLight() {
    TIBONTheme(darkTheme = false) {
        Surface {
            SignUpPage()
        }
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun SignUpPagePreviewDark() {
    TIBONTheme(darkTheme = true) {
        Surface {
            SignUpPage()
        }
    }
}