package com.example.binlookupapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.binlookupapp.data.remote.BinApiService
import com.example.binlookupapp.presentation.ui.screens.HistoryScreen
import com.example.binlookupapp.presentation.ui.screens.MainScreen
import com.example.binlookupapp.ui.theme.BinLookupAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : ComponentActivity() {
    private val binApiService: BinApiService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Тестовый вызов API
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val binInfo = binApiService.getBinInfo("45717360")
                Timber.d("API Response: $binInfo")
            } catch (e: Exception) {
                Timber.e("API Error: ${e.message}")
            }
        }
        setContent {
            BinLookupAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(navController) }
                    composable("history") { HistoryScreen(navController) }
                }
            }
        }
    }
}