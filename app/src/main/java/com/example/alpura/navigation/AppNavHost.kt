package com.example.alpura.navigation

import HomeScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.alpura.screens.article.ArticleListScreen
import com.example.alpura.screens.article.ArticleScreen
import com.example.alpura.screens.login.LoginScreen
import com.example.alpura.screens.register.RegisterScreen
import com.example.alpura.viewmodel.ArticleViewModel
import com.example.alpura.viewmodel.RegisterViewModel

@Composable
fun AppNavHost(navController: NavHostController) {


    val dummyCategories = listOf("Tümü", "Yazılım", "Bilim", "Tarih", "Sanat")

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
            startDestination = Screen.ArticleList.route,
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
            composable(Screen.ArticleList.route) {

                val viewModel: ArticleViewModel = viewModel()
                val articleState = viewModel.articleState.value

                LaunchedEffect(Unit) {
                    viewModel.getAllArticles()
                }

                ArticleListScreen(
                    articles = articleState.articles,
                    categories = listOf("Tümü") + articleState.articles
                        .flatMap { it.category }
                        .map { it.name }
                        .distinct(),
                    onArticleClick = { articleId ->
                        navController.navigate("article/$articleId")
                    })
            }

            composable("article/{articleId}") { backStackEntry ->
                val viewModel: ArticleViewModel = viewModel()

                LaunchedEffect(Unit) {
                    viewModel.getAllArticles()
                }

                val articleId = backStackEntry.arguments?.getString("articleId")
                val article = viewModel.articleState.value.articles.find { it.id == articleId }

                article?.let {
                    ArticleScreen(article = it, onBackClick = { navController.popBackStack() })
                }
            }

        }
    }
}
