package com.example.binlookupapp.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Column {
        Text("Main Screen")
        Button(onClick = { navController.navigate("history") }) {
            Text("Go to History")
        }
    }
}