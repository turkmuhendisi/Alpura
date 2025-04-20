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

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color.White
    ) {

        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_ranking), contentDescription = "Home", Modifier.height(24.dp)) },
            label = { Text("Home") },
            selected = true,
            onClick = { /* Handle home click */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Black
            )
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_learning), contentDescription = "Courses", Modifier.height(24.dp)) },
            label = { Text("Courses") },
            selected = false,
            onClick = { /* Handle courses click */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Black
            )
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_profile), contentDescription = "Profile", Modifier.height(24.dp)) },
            label = { Text("Profile") },
            selected = false,
            onClick = { /* Handle profile click */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Black
            )
        )
    }
}