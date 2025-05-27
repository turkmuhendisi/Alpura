package com.example.alpura.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.alpura.screens.article.ArticleListScreen
import com.example.alpura.screens.article.ArticleScreen
import com.example.alpura.screens.article.TestResultScreen
import com.example.alpura.screens.article.TestScreen
import com.example.alpura.screens.home.HomeScreen
import com.example.alpura.screens.login.LoginScreen
import com.example.alpura.screens.register.RegisterScreen
import com.example.alpura.viewmodel.ArticleViewModel
import com.example.alpura.viewmodel.RegisterViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = currentBackStackEntry?.destination?.route
    val showBottomBar = currentRoute?.startsWith("article/")?.not() == true &&
            currentRoute != Screen.Login.route &&
            currentRoute != Screen.Register.route &&
            currentRoute != Screen.TestScreen.route &&
            currentRoute != Screen.TestResultScreen.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Register.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Register.route) {
                RegisterScreen(
                    navController, registerViewModel = RegisterViewModel()
                )
            }
            composable(Screen.Login.route) { LoginScreen(navController) }
            composable(Screen.ArticleList.route) {

                val viewModel: ArticleViewModel = viewModel()
                val articleState = viewModel.articleState.value

                LaunchedEffect(Unit) {
                    viewModel.getAllArticles()
                }

                ArticleListScreen(articles = articleState.articles,
                    categories = listOf("Tümü") + articleState.articles.flatMap { it.category }
                        .distinct(),
                    onArticleClick = { articleId ->
                        navController.navigate("article/$articleId")
                    })
            }

            composable("article/{articleId}") { backStackEntry ->
                val viewModel: ArticleViewModel = viewModel()

                val articleId = backStackEntry.arguments?.getString("articleId")
                val article = viewModel.articleState.value.articles.find { it.id == articleId }

                LaunchedEffect(Unit) {
                    viewModel.getAllArticles()
                }

                article?.let {

                    ArticleScreen(
                        article = it,
                        onBackClick = { navController.popBackStack() },
                        onTestClick = {
                            navController.navigate(
                                Screen.TestScreen.route.replace(
                                    "{articleId}",
                                    article.id
                                )
                            )
                        })
                }
            }

            composable(
                route = Screen.TestScreen.route,
                arguments = listOf(
                    navArgument("articleId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val viewModel: ArticleViewModel = viewModel()

                val articleId = backStackEntry.arguments?.getString("articleId")

                LaunchedEffect(Unit) {
                    viewModel.getAllArticles()
                }

                val article = viewModel.articleState.value.articles.find { it.id == articleId }

                /*article?.tests?.let { tests ->
                    TestScreen(
                        testQuestions = tests,
                        onTestFinished = { correctAnswers ->
                            navController.navigate(
                                Screen.TestResultScreen.routeWithArgs(
                                    correctAnswers,
                                    tests.size
                                )
                            )
                        },
                        navController = navController
                    )
                }*/
            }

            composable(
                route = Screen.TestResultScreen.route,
                arguments = listOf(
                    navArgument("score") { type = NavType.IntType },
                    navArgument("total") { type = NavType.IntType }
                ),
            ) { backStackEntry ->
                val correctAnswers = backStackEntry.arguments?.getInt("score") ?: 0
                val totalQuestions = backStackEntry.arguments?.getInt("total") ?: 0

                TestResultScreen(
                    correctAnswers = correctAnswers,
                    totalQuestions = totalQuestions,
                    navController = navController
                )
            }
        }
    }
}
