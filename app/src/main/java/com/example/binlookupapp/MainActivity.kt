package com.example.binlookupapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.binlookupapp.data.local.BinHistoryDao
import com.example.binlookupapp.data.local.BinHistoryEntity
import com.example.binlookupapp.data.remote.BinApiService
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
    private val binApiService: BinApiService by inject()
    private val binHistoryDao: BinHistoryDao by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Тестовый вызов API
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val binInfo = binApiService.getBinInfo("45717360")
                Timber.tag("Timber").d("API Response: $binInfo")

                // Тестовая вставка в Room
                val binHistoryEntity = BinHistoryEntity(
                    bin = "45717360",
                    timestamp = System.currentTimeMillis(),
                    scheme = binInfo.scheme,
                    type = binInfo.type,
                    brand = binInfo.brand,
                    countryName = binInfo.country?.name,
                    latitude = binInfo.country?.latitude,
                    longitude = binInfo.country?.longitude,
                    bankName = binInfo.bank?.name,
                    bankUrl = binInfo.bank?.url,
                    bankPhone = binInfo.bank?.phone,
                    bankCity = binInfo.bank?.city
                )
                binHistoryDao.insert(binHistoryEntity)
                Timber.tag("Timber").d("Inserted into Room: $binHistoryEntity")
            } catch (e: Exception) {
                Timber.tag("Timber").e("API Error: ${e.message}")
            }
        }

        // Тестовое получение данных из Room
        CoroutineScope(Dispatchers.IO).launch {
            binHistoryDao.getAll().collectLatest { history ->
                Timber.tag("Timber").d("Room History: $history")
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