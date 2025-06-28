package com.example.tibon.data.local

import androidx.compose.ui.graphics.vector.ImageVector
import java.util.Date

data class TransactionHistory(
    val description: String,
    val nominal: Int,
    val date: Date
)

data class Transaction(
    val name: String,
    val balance: Int,
    val transactionHistory: List<TransactionHistory>
)

data class NavItem (
    val label: String,
    val icon: ImageVector
)
