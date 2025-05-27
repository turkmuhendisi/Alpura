@file:Suppress("NAME_SHADOWING")

package com.example.alpura.screens.main

import com.example.alpura.viewmodel.MainViewModel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.alpura.navigation.AppNavHost
import com.example.alpura.ui.theme.BlueDark

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val isConnected by viewModel.isConnected

    LaunchedEffect(Unit) {
        viewModel.checkConnection()
        println("Bağlantı kontrolü: ${viewModel.isConnected.value}")
    }

    if (!isConnected) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = { viewModel.checkConnection() }) {
                    Text("Tekrar Dene")
                }
            },
            title = { Text("Bağlantı Hatası", color = BlueDark) },
            text = { Text("İnternet bağlantısı yok. Lütfen bağlantınızı kontrol edin.") }
        )
    } else {
        AppNavHost(navController = navController)
    }
}