package com.example.binlookupapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binlookupapp.domain.models.BinInfo
import com.example.binlookupapp.domain.usecases.GetBinInfoUseCase
import com.example.binlookupapp.domain.usecases.InsertHistoryUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getBinInfoUseCase: GetBinInfoUseCase,
    private val insertHistoryUseCase: InsertHistoryUseCase
) : ViewModel() {

    var binInfo = mutableStateOf<BinInfo?>(null)
        private set
    var error = mutableStateOf<String?>(null)
        private set
    var isLoading = mutableStateOf(false)
        private set

    fun fetchBin(bin: String) {
        if (bin.length < 6 || bin.length > 8 || !bin.all { it.isDigit() }) {
            error.value = "BIN должен быть числом от 6 до 8 цифр"
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            try {
                val info = getBinInfoUseCase(bin)
                binInfo.value = info
                insertHistoryUseCase(bin, info)
            } catch (e: Exception) {
                error.value = e.message ?: "Неизвестная ошибка"
            } finally {
                isLoading.value = false
            }
        }
    }
}