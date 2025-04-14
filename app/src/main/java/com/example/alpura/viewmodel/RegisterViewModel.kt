package com.example.alpura.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpura.api.RegisterRequest
import com.example.alpura.api.RetrofitClient
import com.example.alpura.screens.RegisterState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {
    private var _registerState: MutableState<RegisterState> = mutableStateOf(RegisterState())
    val registerState: State<RegisterState> = _registerState
    fun register(
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        viewModelScope.launch {
            _registerState.value = RegisterState()
            if (email.isBlank()) {
                _registerState.value = _registerState.value.copy(
                    errorMessage = "Email alanı boş olamaz.",
                    isEmailFormatError = true
                )
            } else if (password.isBlank()) {
                _registerState.value = _registerState.value.copy(
                    errorMessage = "Şifre alanı boş olamaz.",
                    isPasswordFormatError = true
                )
            } else if (confirmPassword.isBlank()) {
                _registerState.value = _registerState.value.copy(
                    errorMessage = "Şifre alanı boş olamaz.",
                    isPasswordFormatError = true
                )
            } else if (password != confirmPassword) {
                _registerState.value = _registerState.value.copy(
                    errorMessage = "Şifreler uyuşmuyor!",
                    isPasswordsNotMatched = true
                )
            } else {
                withContext(Dispatchers.IO) {
                    _registerState.value = _registerState.value.copy(
                        isLoading = true
                    )

                    val request = RegisterRequest(email, password)
                    val responseDeffered = async {
                        RetrofitClient.apiService.registerUser(request)
                    }
                    val response = responseDeffered.await()

                    if (response.isSuccessful) {
                        _registerState.value = _registerState.value.copy(
                            isValid = true,
                            isLoading = false
                        )
                    } else {
                        _registerState.value = _registerState.value.copy(
                            errorMessage = "E-posta veya şifre hatalı!",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

}
