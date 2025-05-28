package com.example.alpura.navigation

import EducationViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.alpura.screens.article.ArticleListScreen
import com.example.alpura.screens.article.ArticleScreen
import com.example.alpura.screens.education.EducationDetailScreen
import com.example.alpura.screens.education.EducationListScreen
import com.example.alpura.screens.test.TestResultScreen
import com.example.alpura.screens.home.HomeScreen
import com.example.alpura.screens.login.LoginScreen
import com.example.alpura.screens.register.RegisterScreen
import com.example.alpura.screens.test.TestScreen
import com.example.alpura.util.SessionManager
import com.example.alpura.viewmodel.ArticleViewModel
import com.example.alpura.viewmodel.CommentViewModel
import com.example.alpura.viewmodel.RegisterViewModel
import com.example.alpura.viewmodel.TestViewModel

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
            startDestination = Screen.Home.route,
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

                // Argüman ve ViewModel'lar
                val articleId = backStackEntry.arguments?.getString("articleId") ?: return@composable
                val articleViewModel: ArticleViewModel = viewModel()
                val testViewModel: TestViewModel = viewModel()
                val navBackStackEntry = remember(backStackEntry) { backStackEntry }
                val commentViewModel: CommentViewModel = viewModel(navBackStackEntry)

                // Context ve oturumdan kullanıcı adı alma
                val context = LocalContext.current
                val sessionManager = remember { SessionManager(context) }
                val currentUsername = remember { sessionManager.getUsername() ?: "Ziyaretçi" }

                // Mevcut makale
                val article = articleViewModel.articleState.value.articles.find { it.id == articleId }

                // Yorum metni durumu
                val commentText = remember { mutableStateOf("") }

                // Sayfa yüklendiğinde veri getir
                LaunchedEffect(articleId) {
                    articleViewModel.getAllArticles()
                    testViewModel.loadTests(articleId)
                    commentViewModel.loadComments(articleId.toLong())
                }

                // Test olup olmadığını kontrol et
                val testAvailable = testViewModel.testQuestions.isNotEmpty()

                // Ekran gösterimi
                article?.let {
                    ArticleScreen(
                        article = it,
                        comments = commentViewModel.comments,
                        commentText = commentText.value,
                        onCommentTextChange = { commentText.value = it },
                        onSendCommentClick = {
                            commentViewModel.postComment(
                                articleId = it.id.toLong(),
                                username = currentUsername, // Oturumdan gelen kullanıcı adı
                                commentBody = commentText.value
                            ) {
                                commentText.value = ""
                            }
                        },
                        testAvailable = testAvailable,
                        onBackClick = { navController.popBackStack() },
                        onTestClick = {
                            navController.navigate(
                                Screen.TestScreen.route.replace("{articleId}", it.id)
                            )
                        }
                    )
                }
            }

            composable(
                route = Screen.TestScreen.route,
                arguments = listOf(
                    navArgument("articleId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val articleId = backStackEntry.arguments?.getString("articleId") ?: ""
                val viewModel: TestViewModel = viewModel()

                LaunchedEffect(articleId) {
                    viewModel.loadTests(articleId)
                }

                when {
                    viewModel.isLoading -> {
                        // loading indicator
                        androidx.compose.material3.CircularProgressIndicator()
                    }
                    viewModel.errorMessage != null -> {
                        Text("Hata: ${viewModel.errorMessage}")
                    }
                    viewModel.testQuestions.isNotEmpty() -> {
                        TestScreen(
                            testQuestions = viewModel.testQuestions,
                            onTestFinished = { correctAnswers ->
                                navController.navigate(
                                    Screen.TestResultScreen.routeWithArgs(
                                        correctAnswers,
                                        viewModel.testQuestions.size
                                    )
                                )
                            },
                            navController = navController
                        )
                    }
                }
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

            composable(Screen.EducationList.route) {
                val viewModel: EducationViewModel = viewModel()
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    viewModel.loadAllVideos()
                }

                EducationListScreen(
                    videos = viewModel.videos,
                    isLoading = viewModel.isLoading,
                    onVideoClick = { videoId ->
                        navController.navigate(Screen.EducationDetail.routeWithArgs(videoId))
                    },
                    navController = navController,
                    onFetchVideos = { viewModel.loadAllVideos() }
                )
            }

            composable("education_detail/{videoId}") { backStackEntry ->
                val videoId = backStackEntry.arguments?.getString("videoId")?.toLongOrNull() ?: return@composable
                val viewModel: EducationViewModel = viewModel()

                val selectedVideo = viewModel.selectedVideo

                LaunchedEffect(videoId) {
                    viewModel.loadVideoById(videoId)
                }

                selectedVideo?.let {
                    EducationDetailScreen(
                        videoModule = it,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }

        }
    }
}
