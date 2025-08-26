package com.example.binlookupapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber
//import com.example.binlookupapp.di.*

class BinLookupApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@BinLookupApplication)
//            modules(listOf(appModule, networkModule, databaseModule, repositoryModule))
        }
    }
}