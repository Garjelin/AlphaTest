package com.example.binlookupapp.presentation.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.binlookupapp.presentation.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainViewModel = koinViewModel()
    val binInput = remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "BIN Lookup",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = binInput.value,
                onValueChange = { binInput.value = it },
                label = { Text("Enter BIN (6-8 digits)") },
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                contentAlignment = Alignment.Center
            ) {
                viewModel.error.value?.let { errorMessage ->
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Button(
                onClick = { viewModel.fetchBin(binInput.value) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    if (viewModel.binInfo.value == null) {
                        Text(
                            text = "Enter BIN to display information",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        viewModel.binInfo.value?.let { info ->
                            Text("Country: ${info.country?.name ?: "N/A"}")
                            info.country?.latitude?.let { lat ->
                                info.country?.longitude?.let { lon ->
                                    Text(
                                        text = "Coordinates: $lat, $lon",
                                        modifier = Modifier.clickable {
                                            val uri = Uri.parse("geo:$lat,$lon")
                                            val intent = Intent(Intent.ACTION_VIEW, uri)
                                            context.startActivity(intent)
                                        },
                                        color = Color.Blue
                                    )
                                }
                            }
                            Text("Card Type: ${info.scheme ?: "N/A"} / ${info.type ?: "N/A"} / ${info.brand ?: "N/A"}")
                            Text("Bank: ${info.bank?.name ?: "N/A"}")
                            Text(
                                text = "Website: ${info.bank?.url ?: "N/A"}",
                                modifier = if (info.bank?.url != null) {
                                    Modifier.clickable {
                                        val uri = Uri.parse(
                                            if (info.bank.url!!.startsWith("http")) info.bank.url!! else "https://${info.bank.url!!}"
                                        )
                                        val intent = Intent(Intent.ACTION_VIEW, uri)
                                        context.startActivity(intent)
                                    }
                                } else {
                                    Modifier
                                },
                                color = if (info.bank?.url != null) Color.Blue else Color.Black
                            )
                            Text(
                                text = "Phone: ${info.bank?.phone ?: "N/A"}",
                                modifier = if (info.bank?.phone != null) {
                                    Modifier.clickable {
                                        val intent = Intent(
                                            Intent.ACTION_DIAL,
                                            Uri.parse("tel:${info.bank.phone}")
                                        )
                                        context.startActivity(intent)
                                    }
                                } else {
                                    Modifier
                                },
                                color = if (info.bank?.phone != null) Color.Blue else Color.Black
                            )
                            Text("City: ${info.bank?.city ?: "N/A"}")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate("history") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View History")
            }
        }
    }

    if (viewModel.isLoading.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}