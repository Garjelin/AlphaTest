package com.example.binlookupapp.di

import com.example.binlookupapp.domain.usecases.GetBinInfoUseCase
import com.example.binlookupapp.domain.usecases.GetHistoryUseCase
import com.example.binlookupapp.domain.usecases.InsertHistoryUseCase
import com.example.binlookupapp.presentation.viewmodels.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { GetBinInfoUseCase(get()) }
    single { InsertHistoryUseCase(get()) }
    single { GetHistoryUseCase(get()) }
    viewModel { MainViewModel(get(), get()) }
}