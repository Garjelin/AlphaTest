package com.example.binlookupapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.binlookupapp.domain.usecases.GetBinInfoUseCase
import com.example.binlookupapp.domain.usecases.GetHistoryUseCase
import com.example.binlookupapp.domain.usecases.InsertHistoryUseCase
import com.example.binlookupapp.presentation.ui.screens.HistoryScreen
import com.example.binlookupapp.presentation.ui.screens.MainScreen
import com.example.binlookupapp.ui.theme.BinLookupAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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