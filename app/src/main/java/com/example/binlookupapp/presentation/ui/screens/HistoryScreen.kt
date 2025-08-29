package com.example.binlookupapp.presentation.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.binlookupapp.domain.models.BinHistory
import com.example.binlookupapp.presentation.viewmodels.HistoryViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(navController: NavController) {
    val viewModel: HistoryViewModel = koinViewModel()
    val history = viewModel.history.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "История запросов",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (history.isEmpty()) {
            Text(
                text = "История пуста",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(history) { item ->
                    HistoryCard(item)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}

@Composable
fun HistoryCard(item: BinHistory) {
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val date = dateFormat.format(Date(item.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "BIN: ${item.bin}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Дата: $date", style = MaterialTheme.typography.bodySmall)
            Text(text = "Страна: ${item.countryName ?: "N/A"}")
            item.latitude?.let { lat ->
                item.longitude?.let { lon ->
                    Text(
                        text = "Координаты: $lat, $lon",
                        modifier = Modifier.clickable {
                            val uri = Uri.parse("geo:$lat,$lon")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)
                        },
                        color = Color.Blue
                    )
                }
            }
            Text(text = "Тип карты: ${item.scheme ?: "N/A"} / ${item.type ?: "N/A"} / ${item.brand ?: "N/A"}")
            Text(text = "Банк: ${item.bankName ?: "N/A"}")
            item.bankUrl?.let { url ->
                Text(
                    text = "Сайт: $url",
                    modifier = Modifier.clickable {
                        val uri = Uri.parse(if (url.startsWith("http")) url else "https://$url")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    },
                    color = Color.Blue
                )
            }
            item.bankPhone?.let { phone ->
                Text(
                    text = "Телефон: $phone",
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                        context.startActivity(intent)
                    },
                    color = Color.Blue
                )
            }
            Text(text = "Город: ${item.bankCity ?: "N/A"}")
        }
    }
}