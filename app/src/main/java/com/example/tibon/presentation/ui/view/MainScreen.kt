package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.presentation.navigation.Routes
import com.example.tibon.presentation.ui.theme.TIBONTheme
import com.example.tibon.presentation.ui.theme.ThemeSetting

data class NavItem (
    val label: String,
    val icon: ImageVector
)

@Composable
fun MainScreen(
    navController: NavController? = null
) {
    val navItemList = listOf(
        NavItem(
            label = stringResource(id = R.string.home_nav),
            icon = Icons.Filled.Home,
        ),
        NavItem(
            label = stringResource(id = R.string.history_nav),
            icon = Icons.Filled.History,
        ),
        NavItem(
            label = stringResource(id = R.string.profile_nav),
            icon = Icons.Filled.AccountCircle,
        )
    )

    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        label = { Text(text = navItem.label) },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = "${navItem.label} Icon"
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedIndex == 0) {
                FloatingActionButton(
                    onClick = { navController?.navigate(Routes.addAccountPage) },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Tambah Rekening")
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            navController = navController
        )
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    navController: NavController? = null
) {
    when (selectedIndex) {
        0 -> HomePage(modifier, navController)
        1 -> HistoryPage(modifier, navController)
        2 -> ProfilePage(modifier, navController)
    }
}

@Preview(showBackground = true, name="Light Mode")
@Composable
fun MainScreenPreviewLight() {
    TIBONTheme(themeSetting = ThemeSetting.Light) {
        MainScreen()
    }
}

@Preview(showBackground = true, name="Dark Mode")
@Composable
fun MainScreenPreviewDark() {
    TIBONTheme(themeSetting = ThemeSetting.Dark) {
        MainScreen()
    }
}