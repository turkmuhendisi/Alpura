package com.example.alpura.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpura.api.RetrofitClientTest
import com.example.alpura.screens.test.TestQuestion
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {

    var testQuestions by mutableStateOf<List<TestQuestion>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadTests(articleId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val id = articleId.toLong()
                testQuestions = RetrofitClientTest.apiService.getTests(id)
            } catch (e: Exception) {
                errorMessage = "Testler yüklenemedi: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun reset() {
        testQuestions = emptyList()
        errorMessage = null
    }
}
