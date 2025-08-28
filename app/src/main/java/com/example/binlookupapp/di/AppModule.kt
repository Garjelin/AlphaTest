package com.example.binlookupapp.di

import com.example.binlookupapp.domain.usecases.GetBinInfoUseCase
import com.example.binlookupapp.domain.usecases.GetHistoryUseCase
import com.example.binlookupapp.domain.usecases.InsertHistoryUseCase
import org.koin.dsl.module

val appModule = module {
    single { GetBinInfoUseCase(get()) }
    single { InsertHistoryUseCase(get()) }
    single { GetHistoryUseCase(get()) }
}