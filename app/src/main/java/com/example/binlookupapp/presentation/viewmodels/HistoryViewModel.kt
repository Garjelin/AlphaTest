package com.example.binlookupapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binlookupapp.domain.models.BinHistory
import com.example.binlookupapp.domain.usecases.GetHistoryUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HistoryViewModel : ViewModel(), KoinComponent {
    private val getHistoryUseCase: GetHistoryUseCase by inject()

    val history: StateFlow<List<BinHistory>> = getHistoryUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}