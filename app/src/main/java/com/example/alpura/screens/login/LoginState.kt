package com.example.alpura.screens.login

data class LoginState(
    val isLoading:Boolean = false,
    val errorMessage:String ="",
    val isEmailFormatError:Boolean = false,
    val isPasswordFormatError:Boolean=false,
    val isAuthenticated:Boolean = false
)
