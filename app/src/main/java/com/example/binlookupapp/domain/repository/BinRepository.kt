package com.example.binlookupapp.domain.repository

import com.example.binlookupapp.domain.models.BinHistory
import com.example.binlookupapp.domain.models.BinInfo
import kotlinx.coroutines.flow.Flow

interface BinRepository {
    suspend fun getBinInfo(bin: String): BinInfo
    suspend fun insertHistory(bin: String, info: BinInfo)
    fun getHistory(): Flow<List<BinHistory>>
}