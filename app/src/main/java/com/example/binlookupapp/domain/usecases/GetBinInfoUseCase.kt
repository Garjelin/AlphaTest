package com.example.binlookupapp.domain.usecases

import com.example.binlookupapp.domain.models.BinInfo
import com.example.binlookupapp.domain.repository.BinRepository

class GetBinInfoUseCase(private val repository: BinRepository) {
    suspend operator fun invoke(bin: String): BinInfo {
        return repository.getBinInfo(bin)
    }
}