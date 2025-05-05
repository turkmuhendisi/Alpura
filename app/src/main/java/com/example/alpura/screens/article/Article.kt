package com.example.alpura.screens.article

data class Article(
    val id: String,
    val title: String,
    val content: String,
    val imageUrl: String,
    val author: String,
    val created_at: String,
    val updated_at: String,
    val likeStatus: Int,
    val comments: List<String>,
    val commentStatus: Int,
    val category: Set<String>,
    val tests: List<TestQuestion> = emptyList()
)

