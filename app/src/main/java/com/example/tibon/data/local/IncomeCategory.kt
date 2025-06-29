package com.example.tibon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income_categories")
data class IncomeCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)