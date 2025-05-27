package com.example.alpura.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import isConnectedToInternet

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _isConnected = mutableStateOf(true)
    val isConnected: State<Boolean> = _isConnected

    fun checkConnection() {
        val context = getApplication<Application>().applicationContext
        val result = isConnectedToInternet(context)
        println("🔌 İnternet bağlantısı var mı? $result")
        _isConnected.value = result
    }
}
