package com.example.binlookupapp.di

import androidx.room.Room
import com.example.binlookupapp.data.local.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = "bin_db"
        ).build()
    }
    single { get<AppDatabase>().binHistoryDao() }
}