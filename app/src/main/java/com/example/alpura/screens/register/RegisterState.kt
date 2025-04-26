package com.example.alpura.screens.register

data class RegisterState(
    val isLoading: Boolean = false,
    val isValid: Boolean = false,
    val errorMessage:String = "",
    val isEmailFormatError: Boolean = false,
    val isPasswordFormatError: Boolean = false,
    val isPasswordsNotMatched: Boolean = false
)