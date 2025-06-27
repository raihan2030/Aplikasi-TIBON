package com.example.tibon.presentation.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.tibon.presentation.ui.theme.TIBONTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAccountPage(navController: NavController? = null) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.detail_rekening),
                        fontFamily = FontFamily(Font(R.font.poppins_semibold))
                    )
                },
                navigationIcon = {
                    IconButton( onClick = { navController?.navigateUp() } ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.green),
                    titleContentColor = colorResource(R.color.yellow),
                    navigationIconContentColor = colorResource(R.color.yellow)
                )
            )

        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    color = colorResource(R.color.light_green)
                )
        ) {
            Spacer(Modifier.height(15.dp))

            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "Tabunganku",
                fontFamily = FontFamily(Font(R.font.comfortaa_bold)),
                fontSize = 30.sp,
                color = colorResource(R.color.green)
            )

            Spacer(Modifier.height(15.dp))

            Image(
                modifier = Modifier.fillMaxWidth().height(260.dp),
                painter = painterResource(R.drawable.line_chart),
                contentDescription = null
            )

            Spacer(Modifier.height(25.dp))

            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = colorResource(R.color.green),
                        shape = RoundedCornerShape(
                            topStart = 20.dp, topEnd = 20.dp
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Pemasukan:",
                        color = colorResource(R.color.yellow),
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Rp30.000,00",
                        color = colorResource(R.color.yellow),
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(15.dp))
                    Text(
                        text = "Pengeluaran:",
                        color = colorResource(R.color.yellow),
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Rp0,00",
                        color = colorResource(R.color.yellow),
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(15.dp))
                    Text(
                        text = "Total Saldo:",
                        color = colorResource(R.color.yellow),
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Rp30.000,00",
                        color = colorResource(R.color.yellow),
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(70.dp))

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.width(150.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.yellow),
                                contentColor = colorResource(R.color.green)
                            ),
                            onClick = { }
                        ) {
                            Text(
                                text = stringResource(R.string.edit),
                                fontFamily =  FontFamily(Font(R.font.poppins_bold)),
                                fontSize = 25.sp
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Button(
                            modifier = Modifier.width(150.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.yellow),
                                contentColor = colorResource(R.color.green)
                            ),
                            onClick = { }
                        ) {
                            Text(
                                text = stringResource(R.string.hapus),
                                fontFamily =  FontFamily(Font(R.font.poppins_bold)),
                                fontSize = 25.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun DetailAccountPreview(){
    TIBONTheme {
        DetailAccountPage()
    }
}