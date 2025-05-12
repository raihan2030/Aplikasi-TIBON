package com.example.tibon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Language
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tibon.ui.theme.TIBONTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(navController: NavController? = null) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.pengaturan),
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    color = colorResource(R.color.light_green)
                )
        ) {
            SettingsItem(
                name = stringResource(R.string.mode_tema),
                iconVector = Icons.Default.Brush,
                onButtonClick = {  }
            )
            SettingsItem(
                name = stringResource(R.string.bahasa),
                iconVector = Icons.Default.Language,
                onButtonClick = {  }
            )
        }
    }
}

@Composable
fun SettingsItem(
    name: String,
    iconVector: ImageVector,
    onButtonClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = colorResource(R.color.green2)
            ),
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.light_green),
            contentColor = colorResource(R.color.dark_green)
        ),
        onClick = onButtonClick
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = iconVector,
                contentDescription = null
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = name,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 18.sp
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun SettingsPagePreview(){
    TIBONTheme {
        SettingsPage()
    }
}