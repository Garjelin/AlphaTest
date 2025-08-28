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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.binlookupapp.presentation.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainViewModel = koinViewModel()
    val binInput = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = binInput.value,
            onValueChange = { binInput.value = it },
            label = { Text("Введите BIN (6-8 цифр)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.fetchBin(binInput.value) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поиск")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.isLoading.value) {
            CircularProgressIndicator()
        }

        viewModel.error.value?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        viewModel.binInfo.let { info ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Страна: ${info.component1()?.country?.name ?: "N/A"}")
                    info.component1()?.country?.latitude?.let { lat ->
                        info.component1()?.country?.longitude?.let { lon ->
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
                    Text("Тип карты: ${info.component1()?.scheme ?: "N/A"} / ${info.component1()?.type ?: "N/A"} / ${info.component1()?.brand ?: "N/A"}")
                    Text("Банк: ${info.component1()?.bank?.name ?: "N/A"}")
                    info.component1()?.bank?.url?.let { url ->
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
                    info.component1()?.bank?.phone?.let { phone ->
                        Text(
                            text = "Телефон: $phone",
                            modifier = Modifier.clickable {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                                context.startActivity(intent)
                            },
                            color = Color.Blue
                        )
                    }
                    Text("Город: ${info.component1()?.bank?.city ?: "N/A"}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("history") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("История")
        }
    }
}