package com.example.binlookupapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_history")
data class BinHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bin: String,
    val timestamp: Long,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val countryName: String?,
    val latitude: Double?,
    val longitude: Double?,
    val bankName: String?,
    val bankUrl: String?,
    val bankPhone: String?,
    val bankCity: String?
)