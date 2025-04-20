package com.example.alpura.navigation

import HomeScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.alpura.screens.LoginScreen
import com.example.alpura.screens.RegisterScreen
import com.example.alpura.viewmodel.RegisterViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = currentBackStackEntry?.destination?.route
    val showBottomBar = currentRoute != Screen.Login.route && currentRoute != Screen.Register.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Register.route) {
                RegisterScreen(
                    navController,
                    registerViewModel = RegisterViewModel()
                )
            }
            composable(Screen.Login.route) { LoginScreen(navController) }
        }
    }
}
