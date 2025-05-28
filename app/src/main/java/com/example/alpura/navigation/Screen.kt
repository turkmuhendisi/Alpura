package com.example.alpura.navigation

sealed class Screen(val route: String) {
    data object Register : Screen("register")
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object EducationList : Screen("education")
    data object EducationDetail : Screen("education_detail/{videoId}") {
        fun routeWithArgs(videoId: Long) = "education_detail/$videoId"
    }
    data object KnowledgeTunnel : Screen("knowledge_tunnel")
    data object ArticleList : Screen("article_list")
    data object ArticleDetail : Screen("article/{articleId}")
    data object TestScreen : Screen("test_screen/{articleId}")
    data object TestResultScreen : Screen("test_result_screen/{score}/{total}") {
        fun routeWithArgs(correctAnswers: Int, totalQuestions: Int) =
            "test_result_screen/$correctAnswers/$totalQuestions"
    }
}
