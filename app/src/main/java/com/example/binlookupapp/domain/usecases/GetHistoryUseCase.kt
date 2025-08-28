package com.example.binlookupapp.domain.usecases

import com.example.binlookupapp.domain.models.BinHistory
import com.example.binlookupapp.domain.repository.BinRepository
import kotlinx.coroutines.flow.Flow

class GetHistoryUseCase(private val repository: BinRepository) {
    operator fun invoke(): Flow<List<BinHistory>> {
        return repository.getHistory()
    }
}