package com.example.alpura.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alpura.viewmodel.LoginViewModel


@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = LoginViewModel()) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = Color.Black,
            backgroundColor = Color.LightGray
        )
    ) { LoginScreenContent(navController, loginViewModel) }
}

@Composable
fun LoginScreenContent(navController: NavController, loginViewModel: LoginViewModel) {
    val loginState = loginViewModel.loginState.value


    LaunchedEffect(loginState.isAuthenticated) {
        if (loginState.isAuthenticated) {
            navController.navigate("home")
        }

    }

    if (loginState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Loading", color = Color.Black)
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val textFieldColor = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Black,
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black,
        cursorColor = Color.Black
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Giriş Yap",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-posta") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColor,
            shape = RoundedCornerShape(25.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Şifre") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColor,
            shape = RoundedCornerShape(25.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = loginState.errorMessage, color = Color.Red, fontSize = 14.sp)

        Button(
            onClick = {
                loginViewModel.authenticate(
                    email = email,
                    password = password
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            colors = ButtonDefaults.buttonColors(Color.Black),
        ) {
            Text(
                text = "Giriş Yap",
                fontWeight = FontWeight.Black,
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

    }
}