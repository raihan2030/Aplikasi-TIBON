package com.example.tibon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun HistoryPage(modifier: Modifier = Modifier, navController: NavController? = null){
    var text by rememberSaveable { mutableStateOf("") }

    Column (
        modifier = modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.light_green)
            )
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = {
                    Text(
                        text = stringResource(R.string.cari_transaksi),
                        color = colorResource(R.color.gray)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                ,
                shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(R.color.white),
                    unfocusedContainerColor = colorResource(R.color.white),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(start = 10.dp),
                        imageVector = Icons.Default.Search,
                        tint = colorResource(R.color.gray),
                        contentDescription = null
                    )
                },
            )
            Spacer(Modifier.height(15.dp))
            LazyColumn (
                modifier = Modifier.fillMaxWidth()
            ) {
                val allTransactionHistory = transactionList.flatMap { transaction ->
                    transaction.transactionHistory.map { history ->
                        transaction.name to history
                    }
                }
                items(allTransactionHistory) { (name, history) ->
                    HistoryItem(name, history)
                }
            }
        }
    }
}

@Composable
fun HistoryItem(name: String, transactionHistory: TransactionHistory) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(bottom = 0.dp),
        shape = RoundedCornerShape(13.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white_green)
        )
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 15.dp, horizontal = 25.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = name,
                    fontFamily = FontFamily(Font(R.font.comfortaa_bold)),
                    fontSize = 18.sp,
                    color = colorResource(R.color.green)
                )
                Spacer(Modifier.weight(1f))
                if(transactionHistory.nominal > 0){
                    Text(
                        text = "+${rupiahFormat(transactionHistory.nominal)}",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = colorResource(R.color.green)
                    )
                }
                else{
                    Text(
                        text = rupiahFormat(transactionHistory.nominal),
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = colorResource(R.color.red)
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = transactionHistory.description,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 14.sp
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = formatter.format(transactionHistory.date),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 14.sp
                )
            }
        }
    }
    Spacer(Modifier.height(10.dp))
}

@Preview(widthDp = 360)
@Composable
fun HistoryItemPreview() {
    TIBONTheme {
        HistoryItem(transactionList[0].name, transactionHistory1[0])
    }
}


@Preview(
    widthDp = 360,
    heightDp = 800,
    showBackground = true
)
@Composable
fun HistoryPagePreview() {
    TIBONTheme {
        HistoryPage()
    }
}