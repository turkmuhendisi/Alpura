package com.example.alpura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alpura.screens.HomeScreen
import com.example.alpura.screens.RegisterScreen
import com.example.alpura.ui.theme.AlpuraTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlpuraTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "register") {
                    composable("register") {
                        RegisterScreen(navController)
                    }
                    composable("home") {
                        // Ana ekran
                        HomeScreen()
                    }
                }
            }
        }
    }
}
