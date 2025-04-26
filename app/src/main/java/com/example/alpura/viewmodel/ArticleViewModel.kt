package com.example.alpura.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpura.api.RetrofitClient
import com.example.alpura.screens.article.ArticleState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel() : ViewModel() {
    private var _articleState: MutableState<ArticleState> = mutableStateOf(ArticleState())
    val articleState: State<ArticleState> = _articleState

    fun getAllArticles() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _articleState.value =
                        _articleState.value.copy(isLoading = true, errorMessage = "")

                    val responseDeffered = async {
                        RetrofitClient.apiService.getAllArticles()
                    }

                    val articles = responseDeffered.await()

                    _articleState.value = _articleState.value.copy(
                        isLoading = false,
                        articles = articles
                    )
                } catch (e: Exception) {
                    _articleState.value = _articleState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Bir hata oluştu!"
                    )
                }
            }
        }
    }
}