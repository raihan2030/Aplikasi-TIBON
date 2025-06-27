package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.presentation.navigation.Routes
import com.example.tibon.presentation.ui.theme.TIBONTheme

@Composable
fun LoginPage(navController: NavController? = null) {
    var emailText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.green)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(60.dp))
            Text(
                text = stringResource(R.string.welcome),
                color = colorResource(R.color.yellow),
                fontFamily = FontFamily(Font(R.font.comfortaa_bold)),
                fontSize = 35.sp
            )
            Spacer(Modifier.height(60.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                Spacer(Modifier.height(75.dp))

                TextInput(
                    text = emailText,
                    onTextChange = { emailText = it },
                    label = stringResource(R.string.email_label),
                    title = stringResource(R.string.email)
                )

                Spacer(Modifier.height(20.dp))

                PasswordInput(
                    passwordText = passwordText,
                    onPasswordChange = { passwordText = it },
                    passwordVisible = passwordVisible,
                    onVisibilityChange = { passwordVisible = !passwordVisible },
                    title = stringResource(R.string.password)
                )

                Spacer(Modifier.height(60.dp))

                Button(
                    modifier = Modifier.size(width = 240.dp, height = 55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.green),
                        contentColor = colorResource(R.color.yellow)
                    ),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                    onClick = {
                        navController?.navigate(Routes.mainScreenPage)
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.login),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                TextButton(
                    onClick = {
                        navController?.navigate(Routes.signUpPage)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.dont_have_account),
                        color = colorResource(R.color.dark_green)
                    )
                }
            }
        }
    }
}


@Composable
fun TextInput(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    title: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp),
        text = title,
        fontFamily = FontFamily(Font(R.font.poppins_medium)),
        color = colorResource(R.color.dark_green)
    )
    Spacer(Modifier.height(3.dp))
    TextField(
        value = text,
        onValueChange = onTextChange,
        label = {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = label
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp, horizontal = 20.dp)
        ,
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.white_green),
            unfocusedContainerColor = colorResource(R.color.white_green),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun PasswordInput(
    passwordText: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onVisibilityChange: () -> Unit,
    title: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 45.dp),
            text = title,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            color = colorResource(R.color.dark_green)
        )
        Spacer(Modifier.height(3.dp))
        TextField(
            value = passwordText,
            onValueChange = onPasswordChange,
            label = {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(R.string.password_label)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(25.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Default.Visibility
                else
                    Icons.Default.VisibilityOff

                IconButton(onClick = onVisibilityChange) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.white_green),
                unfocusedContainerColor = colorResource(R.color.white_green),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}


@Preview(
    widthDp = 360,
    heightDp = 800,
    showBackground = true
)
@Composable
fun LoginPagePreview() {
    TIBONTheme {
        LoginPage()
    }
}