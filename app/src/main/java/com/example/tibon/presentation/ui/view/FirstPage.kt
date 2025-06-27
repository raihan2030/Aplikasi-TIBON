package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
fun FirstPage(navController: NavController? = null){
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.light_green)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.width(120.dp),
                painter = painterResource(R.drawable.wallet_12519859),
                colorFilter = ColorFilter.tint(colorResource(R.color.green)),
                contentDescription = null
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = stringResource(R.string.app_name),
                fontFamily = FontFamily(Font(R.font.comfortaa_bold)),
                fontSize = 50.sp,
                color = colorResource(R.color.green)
            )
            Text(
                text = stringResource(R.string.tagline),
                fontFamily = FontFamily(Font(R.font.comfortaa_bold)),
                fontSize = 16.sp,
                color = colorResource(R.color.green)
            )
            Spacer(Modifier.height(15.dp))
            Button(
                modifier = Modifier.size(width = 240.dp, height = 55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.green),
                    contentColor = colorResource(R.color.yellow)
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                onClick = {
                    navController?.navigate(Routes.loginPage)
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
            Spacer(Modifier.height(10.dp))
            Button(
                modifier = Modifier.size(width = 240.dp, height = 55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.white),
                    contentColor = colorResource(R.color.green)
                ),
                contentPadding = PaddingValues(
                    horizontal = 24.dp,
                    vertical = 12.dp
                ),
                onClick = {
                    navController?.navigate(Routes.signUpPage)
                }
            ) {
                Box(
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
        }
    }
}

@Preview(
    widthDp = 360,
    heightDp = 800,
    showBackground = true
)
@Composable
fun FirstPagePreview(){
    TIBONTheme {
        FirstPage()
    }
}