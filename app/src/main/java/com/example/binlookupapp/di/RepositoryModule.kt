package com.example.binlookupapp.di

import com.example.binlookupapp.data.repository.BinRepositoryImpl
import com.example.binlookupapp.domain.repository.BinRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<BinRepository> { BinRepositoryImpl(get(), get()) }
}