package com.example.alpura.navigation

sealed class Screen(val route: String) {
    data object Register : Screen("register")
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object Education : Screen("education")
    data object KnowledgeTunnel : Screen("knowledge_tunnel")
    data object Blog : Screen("blog")
}
