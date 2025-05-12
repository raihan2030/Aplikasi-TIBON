package com.example.tibon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountPage(navController: NavController? = null) {
    var nameText by rememberSaveable { mutableStateOf("") }
    var initialBalanceText by rememberSaveable { mutableStateOf("") }
    var notesText by rememberSaveable { mutableStateOf("") }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tambah_rekening),
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
            TextInput(
                text = nameText,
                onTextChange = { nameText = it },
                label = "Nama Rekening Baru",
                title = "Nama Rekening:"
            )
            Spacer(Modifier.height(15.dp))
            TextInput(
                text = initialBalanceText,
                onTextChange = { initialBalanceText = it },
                label = "Saldo Awal (Opsional)",
                title = "Saldo Awal:"
            )
            Spacer(Modifier.height(15.dp))
            TextInput(
                text = notesText,
                onTextChange = { notesText = it },
                label = "Catatan (Opsional)",
                title = "Catatan:"
            )
            Spacer(Modifier.height(25.dp))
            Button(
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
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
                        text = stringResource(R.string.simpan),
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
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun AddAccountPreview() {
    TIBONTheme {
        AddAccountPage()
    }
}