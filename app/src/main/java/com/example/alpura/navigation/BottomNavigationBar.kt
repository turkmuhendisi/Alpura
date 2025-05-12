package com.example.alpura.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alpura.R
import com.example.alpura.ui.theme.Blue
import com.example.alpura.ui.theme.NavyBlue

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentDestination = navController.currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White
    ) {

        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(R.drawable.ic_ranking),
                    contentDescription = "Status",
                    Modifier.height(24.dp)
                )
            },
            label = { Text("Durum") },
            selected = currentDestination == "home",
            onClick = {
                if (currentDestination != "home") {
                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Blue,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Blue
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(R.drawable.ic_learning),
                    contentDescription = "Articles",
                    Modifier.height(24.dp)
                )
            },
            label = { Text("Yazılar") },
            selected = currentDestination == "article_list" || currentDestination == "article/{articleId}",
            onClick = {
                if (currentDestination != "article_list") {
                    navController.navigate("article_list") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Blue,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Blue
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(R.drawable.ic_profile),
                    contentDescription = "Profile",
                    Modifier.height(24.dp)
                )
            },
            label = { Text("Profil") },
            selected = false,
            onClick = { /* Handle profile click */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Blue,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Blue
            )
        )
    }
}