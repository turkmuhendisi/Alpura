package com.example.alpura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alpura.screens.main.MainScreen
import com.example.alpura.ui.theme.AlpuraTheme
import com.example.alpura.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlpuraTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel()

                MainScreen(
                    viewModel = viewModel,
                    navController = navController
                    )

                val color = MaterialTheme.colorScheme.background
                val darkIcons = color.luminance() > 0.5f
                SideEffect {
                    window.statusBarColor = color.toArgb()
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightStatusBars = darkIcons
                }
            }

        }
    }
}
