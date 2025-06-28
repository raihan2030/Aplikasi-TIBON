package com.example.tibon.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tibon.presentation.navigation.Routes
import com.example.tibon.presentation.ui.theme.TIBONTheme
import com.example.tibon.presentation.ui.theme.ThemeSetting
import com.example.tibon.presentation.ui.view.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentTheme by remember { mutableStateOf(ThemeSetting.System) }

            TIBONTheme(themeSetting = currentTheme) {
                MainApp(
                    currentTheme = currentTheme,
                    onThemeChange = { newTheme -> currentTheme = newTheme }
                )
            }
        }
    }
}

@Composable
fun MainApp(
    currentTheme: ThemeSetting,
    onThemeChange: (ThemeSetting) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.firstPage) {
        composable(Routes.firstPage) { FirstPage(navController) }
        composable(Routes.loginPage) { LoginPage(navController) }
        composable(Routes.signUpPage) { SignUpPage(navController) }
        composable(Routes.mainScreenPage) { MainScreen(navController = navController) }
        composable(Routes.detailAccountPage) { DetailAccountPage(navController) }
        composable(Routes.addAccountPage) { AddAccountPage(navController) }
        composable(Routes.editAccountPage) { EditAccountPage(navController) }
        composable(Routes.settingsPage) {
            SettingsPage(
                navController = navController,
                currentTheme = currentTheme,
                onThemeChange = onThemeChange
            )
        }
        composable(
            route = Routes.addTransactionPage + "/{transactionType}",
            arguments = listOf(navArgument("transactionType") { type = NavType.StringType })
        ) { backStackEntry ->
            AddTransactionPage(
                navController = navController,
                transactionType = backStackEntry.arguments?.getString("transactionType")
            )
        }
    }
}