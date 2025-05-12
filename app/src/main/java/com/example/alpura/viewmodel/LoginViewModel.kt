package com.example.alpura.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpura.api.LoginRequest
import com.example.alpura.api.RetrofitClientUser
import com.example.alpura.screens.login.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel() : ViewModel() {
    private var _loginState: MutableState<LoginState> = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    fun authenticate(
        email: String,
        password: String,
    ) {

        viewModelScope.launch {

            _loginState.value = LoginState()
            if (email.isBlank()) {
                _loginState.value = _loginState.value.copy(
                    errorMessage = "Email boş olmaz",
                    isEmailFormatError = true

                )
            } else if (password.isBlank()) {
                _loginState.value = _loginState.value.copy(
                    errorMessage = "şifre boş olmaz",
                    isPasswordFormatError = true

                )
            } else {
                withContext(Dispatchers.IO) {
                    _loginState.value = _loginState.value.copy(
                        isLoading = true
                    )
                    val request = LoginRequest(email, password)
                    val responseDeferred = async {
                        RetrofitClientUser.apiService.loginUser(request)
                    }
                    val response = responseDeferred.await()

                    if (response.isSuccessful) {
                        _loginState.value = _loginState.value.copy(
                            isAuthenticated = true,
                            isLoading = false
                        )
                    } else {
                        _loginState.value = _loginState.value.copy(
                            errorMessage = "yanlış bilgiler",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}