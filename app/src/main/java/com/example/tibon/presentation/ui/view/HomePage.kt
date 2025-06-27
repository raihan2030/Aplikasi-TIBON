package com.example.tibon.presentation.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tibon.R
import com.example.tibon.data.local.Transaction
import com.example.tibon.data.local.rupiahFormat
import com.example.tibon.data.local.transactionList
import com.example.tibon.presentation.navigation.Routes
import com.example.tibon.presentation.ui.theme.TIBONTheme

@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController? = null){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val orientation = configuration.orientation
    val innerBackgroundHeight = when(orientation) {
        Configuration.ORIENTATION_PORTRAIT -> screenHeight * 0.8f
        else -> screenHeight * 1.2f
    }

    LazyColumn (
        modifier = modifier
            .fillMaxWidth()
            .height(screenHeight)
            .background(
                color = colorResource(R.color.green)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TopItem()
        }

        item {
            Column (
                modifier = Modifier
                    .background(
                        color = colorResource(R.color.light_green),
                        shape = RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp
                        )
                    )
                    .fillMaxWidth()
                    .height(innerBackgroundHeight)
            ) {
                Spacer(Modifier.height(60.dp))
                Row (
                    modifier = Modifier.padding(horizontal = 25.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(130.dp),
                        painter = painterResource(R.drawable.pie_chart),
                        contentDescription = null
                    )
                    Spacer(Modifier.weight(1f))
                    Column {
                        Row {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(top = 5.dp),
                                imageVector = Icons.Default.Circle,
                                tint = colorResource(R.color.green),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(3.dp))
                            Column {
                                Text(
                                    text = "Total Pemasukan:",
                                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                    fontSize = 14.sp,
                                    color = colorResource(R.color.dark_green)
                                )
                                Text(
                                    text = "Rp 100.000,00",
                                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                    color = colorResource(R.color.dark_green)
                                )
                            }
                        }
                        Spacer(Modifier.height(40.dp))
                        Row {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(top = 5.dp),
                                imageVector = Icons.Default.Circle,
                                tint = colorResource(R.color.yellow),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(3.dp))
                            Column {
                                Text(
                                    text = "Total Pengeluaran:",
                                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                    fontSize = 14.sp,
                                    color = colorResource(R.color.dark_green)
                                )
                                Text(
                                    text = "Rp 50.000,00",
                                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                    color = colorResource(R.color.dark_green)
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(60.dp))
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.daftar_rekening),
                            fontFamily = FontFamily(Font(R.font.comfortaa_bold)),
                            color = colorResource(R.color.dark_green)
                        )
                        Spacer(Modifier.weight(1f))
                        Button(
                            modifier = Modifier
                                .size(width = 70.dp, height = 30.dp)
                                .padding(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.yellow),
                                contentColor = colorResource(R.color.green)
                            ),
                            onClick = { navController?.navigate(Routes.addAccountPage) }
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                    LazyColumn (
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(transactionList) { transaction ->
                            AccountItem(transaction, navController)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun TopItem() {
    Row (
        modifier = Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon",
                tint = colorResource(R.color.yellow)
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    lineHeight = 10.sp,
                    text = stringResource(R.string.hi_welcome),
                    color = colorResource(R.color.yellow),
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 12.sp
                )
                Text(
                    lineHeight = 5.sp,
                    text = stringResource(R.string.username),
                    color = colorResource(R.color.yellow),
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    fontSize = 18.sp
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {  }
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.Notifications,
                contentDescription = "Icon",
                tint = colorResource(R.color.yellow)
            )
        }
    }
}

@Composable
fun AccountItem(transaction: Transaction, navController: NavController? = null) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .height(80.dp),
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.green)
        ),
        onClick = { navController?.navigate(Routes.detailAccountPage) }
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 15.dp, horizontal = 20.dp)
        ) {
            Text(
                text = transaction.name,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 20.sp,
                color = colorResource(R.color.yellow)
            )
            Spacer(Modifier.weight(1f))
            Row {
                Text(
                    text = stringResource(R.string.total_saldo),
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = rupiahFormat(transaction.balance),
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                )
            }
        }
    }
}

@Preview(widthDp = 360)
@Composable
fun AccountItemPreview(){
    TIBONTheme {
        AccountItem(transactionList[0])
    }
}

@Preview(
    widthDp = 360,
    heightDp = 800,
    showBackground = true
)
@Composable
fun HomePagePreview() {
    TIBONTheme {
        HomePage()
    }
}