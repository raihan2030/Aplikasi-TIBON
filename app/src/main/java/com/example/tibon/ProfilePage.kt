package com.example.tibon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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

@Composable
fun ProfilePage(modifier: Modifier = Modifier, navController: NavController? = null){
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.light_green)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))


        Box(
            modifier = Modifier.size(200.dp)
        ) {
            Icon(
                modifier = Modifier.size(200.dp).align(Alignment.Center),
                imageVector = Icons.Default.AccountCircle,
                tint = colorResource(R.color.green),
                contentDescription = "Profile Icon"
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp).size(50.dp),
                onClick = {}
            ) {
                Box {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = "Edit Profile Photo",
                        tint = colorResource(R.color.yellow),
                        modifier = Modifier.size(50.dp).align(Alignment.Center)
                    )
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile Photo",
                        tint = colorResource(R.color.green),
                        modifier = Modifier.size(30.dp).align(Alignment.Center)
                    )
                }
            }
        }

        Text(
            text = "Username",
            fontFamily = FontFamily(Font(R.font.comfortaa_bold)),
            fontSize = 35.sp,
            color = colorResource(R.color.green)
        )
        Spacer(Modifier.height(50.dp))

        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            ProfileItem(
                iconVector = Icons.Default.Settings,
                name = stringResource(R.string.pengaturan),
                onButtonClick = { navController?.navigate(Routes.settingsPage) }
            )
            ProfileItem(
                iconVector = Icons.Default.SupportAgent,
                name = stringResource(R.string.bantuan_dukungan),
                onButtonClick = {  }
            )
            ProfileItem(
                iconVector = Icons.AutoMirrored.Filled.Logout,
                name = stringResource(R.string.logout),
                onButtonClick = { navController?.navigate(Routes.firstPage) }
            )
        }
    }
}

@Composable
fun ProfileItem(
    iconVector: ImageVector,
    name: String,
    onButtonClick: () -> Unit
) {
    Button (
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.green)
        ),
        onClick = onButtonClick
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = iconVector,
                contentDescription = null,
                tint = colorResource(R.color.yellow)
            )
            Spacer(Modifier.width(15.dp))
            Text(
                text = name,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 18.sp,
                color = colorResource(R.color.yellow)
            )
        }
    }
    Spacer(Modifier.height(10.dp))
}

@Preview(
    widthDp = 360,
    heightDp = 800,
    showBackground = true
)
@Composable
fun ProfilePagePreview() {
    TIBONTheme {
        ProfilePage()
    }
}