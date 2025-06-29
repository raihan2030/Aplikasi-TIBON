package com.example.tibon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val notes: String? = null,
    val type: String,
    val notificationSchedule: String = "NONE",
    val notificationHour: Int = 8,
    val notificationMinute: Int = 0
)