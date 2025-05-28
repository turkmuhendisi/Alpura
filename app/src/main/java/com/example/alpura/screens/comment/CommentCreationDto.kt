package com.example.alpura.screens.comment

data class CommentCreationDto(
    val body: String,
    val username: String,
    val articleId: Long
)