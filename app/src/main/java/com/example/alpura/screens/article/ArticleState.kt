package com.example.alpura.screens.article

data class ArticleState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val errorMessage: String = ""
)
