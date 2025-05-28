package com.example.alpura.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpura.api.RetrofitClientComment
import com.example.alpura.screens.comment.CommentCreationDto
import com.example.alpura.screens.comment.CommentResponseDto
import kotlinx.coroutines.launch

class CommentViewModel : ViewModel() {

    var comments by mutableStateOf<List<CommentResponseDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadComments(articleId: Long) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val updatedComments = RetrofitClientComment.apiService.getComments(articleId)
                comments = updatedComments.toList()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun postComment(
        articleId: Long,
        username: String,
        commentBody: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                RetrofitClientComment.apiService.postComment(
                    CommentCreationDto(
                        articleId = articleId,
                        username = username,
                        body = commentBody
                    )
                )

                val updatedComments = RetrofitClientComment.apiService.getComments(articleId)
                comments = updatedComments.toList()

                onSuccess()
            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }
}
