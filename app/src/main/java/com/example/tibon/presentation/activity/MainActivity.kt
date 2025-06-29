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
import androidx.navigation.navDeepLink
import com.example.tibon.presentation.navigation.Routes
import com.example.tibon.presentation.ui.theme.TIBONTheme
import com.example.tibon.presentation.ui.theme.ThemeSetting
import com.example.tibon.presentation.ui.view.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        composable(
            route = Routes.detailAccountPage + "/{accountId}",
            arguments = listOf(navArgument("accountId") { type = NavType.IntType }),
            deepLinks = listOf(navDeepLink { uriPattern = "tibon://account/{accountId}" })
        ) { backStackEntry ->
            DetailAccountPage(
                navController = navController,
                accountId = backStackEntry.arguments?.getInt("accountId") ?: 0
            )
        }
        composable(Routes.addAccountPage) { AddAccountPage(navController) }
        composable(
            route = Routes.editAccountPage + "/{accountId}",
            arguments = listOf(navArgument("accountId") { type = NavType.IntType })
        ) { backStackEntry ->
            EditAccountPage(
                navController = navController,
                accountId = backStackEntry.arguments?.getInt("accountId") ?: 0
            )
        }
        composable(Routes.settingsPage) {
            SettingsPage(
                navController = navController,
                currentTheme = currentTheme,
                onThemeChange = onThemeChange
            )
        }
        composable(
            route = Routes.addTransactionPage + "/{accountId}/{transactionType}",
            arguments = listOf(
                navArgument("accountId") { type = NavType.IntType },
                navArgument("transactionType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            AddTransactionPage(
                navController = navController,
                accountId = backStackEntry.arguments?.getInt("accountId") ?: 0,
                transactionType = backStackEntry.arguments?.getString("transactionType") ?: "Pengeluaran"
            )
        }
        composable(
            route = Routes.accountHistoryPage + "/{accountId}",
            arguments = listOf(navArgument("accountId") { type = NavType.IntType })
        ) { backStackEntry ->
            AccountHistoryPage(
                navController = navController,
                accountId = backStackEntry.arguments?.getInt("accountId") ?: 0
            )
        }
        composable(Routes.allAccountsPage) {
            AllAccountsPage(navController = navController)
        }
        composable(Routes.currencySettingsPage) {
            CurrencySettingsPage(navController = navController)
        }
    }
}