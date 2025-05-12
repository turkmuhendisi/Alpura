package com.example.alpura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.example.alpura.navigation.AppNavHost
import android.os.Build
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.example.alpura.ui.theme.AlpuraTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlpuraTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
                // Status bar rengini temaya göre ayarla
                val color = MaterialTheme.colorScheme.background
                val darkIcons = MaterialTheme.colorScheme.background.luminance() > 0.5f
                SideEffect {
                    window.statusBarColor = color.toArgb()
                    WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars = darkIcons
                }
            }
        }
    }
}
