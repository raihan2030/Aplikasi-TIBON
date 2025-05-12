package com.example.tibon

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController? = null) {
    val navItemList = listOf(
        NavItem(Icons.Default.Home),
        NavItem(Icons.Default.History),
        NavItem(Icons.Default.AccountCircle)
    )

    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar (
                containerColor = colorResource(R.color.green),
            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = navItem.icon,
                                contentDescription = "Icon",
                                tint = colorResource(R.color.yellow)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = colorResource(R.color.green2)
                        )
                    )
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
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int, navController: NavController? = null){
    when(selectedIndex){
        0 -> HomePage(modifier, navController)
        1 -> HistoryPage(modifier, navController)
        2 -> ProfilePage(modifier, navController)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}