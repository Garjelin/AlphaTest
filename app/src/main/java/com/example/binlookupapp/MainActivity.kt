package com.example.binlookupapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.binlookupapp.presentation.ui.screens.HistoryScreen
import com.example.binlookupapp.presentation.ui.screens.MainScreen
import com.example.binlookupapp.ui.theme.BinLookupAppTheme

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