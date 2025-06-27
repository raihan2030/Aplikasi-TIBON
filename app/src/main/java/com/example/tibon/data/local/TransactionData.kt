package com.example.tibon.data.local

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

val formatter = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))

@Composable
fun rupiahFormat(nominal: Int): String {
    val formatter = remember {
        NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    }
    return formatter.format(nominal)
}

val transactionHistory1 = listOf(
    TransactionHistory(
        description = "Sisa uang saku",
        nominal = 15000,
        date = formatter.parse("23 Nov 2024")!!
    ),
    TransactionHistory(
        description = "Dapat bonus",
        nominal = 15000,
        date = formatter.parse("13 Nov 2024")!!
    )
)

val transactionHistory2 = listOf(
    TransactionHistory(
        description = "Mouse terjual",
        nominal = 70000,
        date = formatter.parse("4 Okt 2024")!!
    ),
    TransactionHistory(
        description = "Beli Sparepart",
        nominal = -50000,
        date = formatter.parse("1 Okt 2024")!!
    )
)

val transactionList = listOf(
    Transaction(
        name = "Tabunganku",
        balance = 30000,
        transactionHistory = transactionHistory1
    ),
    Transaction(
        name = "Toko Komputer",
        balance = 20000,
        transactionHistory = transactionHistory2
    )
)