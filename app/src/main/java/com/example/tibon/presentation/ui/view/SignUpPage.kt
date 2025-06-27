package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.presentation.navigation.Routes
import com.example.tibon.presentation.ui.theme.TIBONTheme

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

                TextInput(
                    text = mobileNumText,
                    onTextChange = { mobileNumText = it },
                    placeholder = stringResource(R.string.mobile_num_placeholder),
                    title = stringResource(R.string.mobile_number)
                )
                Spacer(Modifier.height(16.dp))

                TextInput(
                    text = dateOfBirthText,
                    onTextChange = { dateOfBirthText = it },
                    placeholder = stringResource(R.string.date_placeholder),
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