package com.example.tibon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tibon.ui.theme.TIBONTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TIBONTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.firstPage, builder = {
        composable(Routes.firstPage) {
            FirstPage(navController)
        }
        composable(Routes.loginPage) {
            LoginPage(navController)
        }
        composable(Routes.signUpPage) {
            SignUpPage(navController)
        }
        composable(Routes.mainScreenPage) {
            MainScreen(navController)
        }
        composable(Routes.detailAccountPage) {
            DetailAccountPage(navController)
        }
        composable(Routes.addAccountPage) {
            AddAccountPage(navController)
        }
        composable(Routes.settingsPage) {
            SettingsPage(navController)
        }
    })
}