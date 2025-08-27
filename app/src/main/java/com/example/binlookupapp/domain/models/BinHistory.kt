package com.example.binlookupapp.domain.models

data class BinHistory(
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
