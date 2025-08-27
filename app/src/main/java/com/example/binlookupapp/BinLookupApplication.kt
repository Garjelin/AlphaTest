package com.example.binlookupapp

import android.app.Application
import com.example.binlookupapp.di.appModule
import com.example.binlookupapp.di.databaseModule
import com.example.binlookupapp.di.networkModule
import com.example.binlookupapp.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class BinLookupApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@BinLookupApplication)
            modules(listOf(appModule, networkModule, databaseModule, repositoryModule))
        }
    }
}