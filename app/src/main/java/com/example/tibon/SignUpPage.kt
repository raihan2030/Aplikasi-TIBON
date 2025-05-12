package com.example.tibon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tibon.ui.theme.TIBONTheme

@Composable
fun SignUpPage(navController: NavController? = null) {
    var fullNameText by rememberSaveable { mutableStateOf("") }
    var emailText by rememberSaveable { mutableStateOf("") }
    var mobileNumText by rememberSaveable { mutableStateOf("") }
    var dateOfBirthText by rememberSaveable { mutableStateOf("") }

    var passwordText by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confPasswordText by rememberSaveable { mutableStateOf("") }
    var confPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold {innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .background(colorResource(R.color.green)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(50.dp))
            Text(
                text = stringResource(R.string.create_account),
                color = colorResource(R.color.yellow),
                fontFamily = FontFamily(Font(R.font.comfortaa_bold)),
                fontSize = 30.sp
            )
            Spacer(Modifier.height(50.dp))
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        color = colorResource(R.color.light_green),
                        shape = RoundedCornerShape(
                            topStart = 50.dp,
                            topEnd = 50.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(15.dp))

                TextInput(
                    text = fullNameText,
                    onTextChange = { fullNameText = it },
                    label = stringResource(R.string.full_name_label),
                    title = stringResource(R.string.full_name)
                )

                Spacer(Modifier.height(10.dp))

                TextInput(
                    text = emailText,
                    onTextChange = { emailText = it },
                    label = stringResource(R.string.email_label),
                    title = stringResource(R.string.email)
                )

                Spacer(Modifier.height(10.dp))

                TextInput(
                    text = mobileNumText,
                    onTextChange = { mobileNumText = it },
                    label = stringResource(R.string.mobile_num_label),
                    title = stringResource(R.string.mobile_number)
                )

                Spacer(Modifier.height(10.dp))

                TextInput(
                    text = dateOfBirthText,
                    onTextChange = { dateOfBirthText = it },
                    label = stringResource(R.string.date_label),
                    title = stringResource(R.string.date_of_birth)
                )

                Spacer(Modifier.height(10.dp))

                PasswordInput(
                    passwordText = passwordText,
                    onPasswordChange = { passwordText = it },
                    passwordVisible = passwordVisible,
                    onVisibilityChange = { passwordVisible = !passwordVisible },
                    title = stringResource(R.string.password)
                )

                Spacer(Modifier.height(10.dp))

                PasswordInput(
                    passwordText = confPasswordText,
                    onPasswordChange = { confPasswordText = it },
                    passwordVisible = confPasswordVisible,
                    onVisibilityChange = { confPasswordVisible = !confPasswordVisible },
                    title = stringResource(R.string.confirm_password)
                )

                Spacer(Modifier.height(60.dp))

                Button(
                    modifier = Modifier.size(width = 240.dp, height = 55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.white),
                        contentColor = colorResource(R.color.green)
                    ),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                    onClick = {
                        navController?.navigate(Routes.loginPage)
                    }
                ) {
                    Box (
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.signup),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
                TextButton(
                    onClick = {
                        navController?.navigate(Routes.loginPage)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.already_have_account),
                        color = colorResource(R.color.dark_green)
                    )
                }

                Spacer(Modifier.height(60.dp))
            }
        }
    }

}

@Preview(
    widthDp = 360,
    heightDp = 800,
    showBackground = true
)
@Composable
fun SignUpPagePreview() {
    TIBONTheme {
        SignUpPage()
    }
}