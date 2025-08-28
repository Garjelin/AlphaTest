package com.example.binlookupapp.domain.usecases

import com.example.binlookupapp.domain.models.BinInfo
import com.example.binlookupapp.domain.repository.BinRepository

class InsertHistoryUseCase(private val repository: BinRepository) {
    suspend operator fun invoke(bin: String, info: BinInfo) {
        repository.insertHistory(bin, info)
    }
}