package com.example.binlookupapp.domain.models

data class BinInfo(
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val country: Country?,
    val bank: Bank?
)

data class Country(
    val name: String?,
    val latitude: Double?,
    val longitude: Double?
)

data class Bank(
    val name: String?,
    val url: String?,
    val phone: String?,
    val city: String?
)
