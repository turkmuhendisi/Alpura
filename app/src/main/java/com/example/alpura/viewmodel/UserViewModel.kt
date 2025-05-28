package com.example.alpura.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.alpura.util.SessionManager

class UserViewModel(context: Context) : ViewModel() {

    private val sessionManager = SessionManager(context)

    fun getUsername(): String? = sessionManager.getUsername()
}
