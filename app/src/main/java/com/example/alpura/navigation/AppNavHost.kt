package com.example.alpura.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.alpura.screens.HomeScreen
import com.example.alpura.screens.LoginScreen
import com.example.alpura.screens.RegisterScreen
import com.example.alpura.viewmodel.RegisterViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Register.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController, registerViewModel = RegisterViewModel())}
        composable(Screen.Login.route) { LoginScreen(navController) }
    }
}
