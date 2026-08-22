package com.codefreaks.aegiscall.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.codefreaks.aegiscall.data.repository.AuthRepository

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onCreateAccount: () -> Unit
) {

    val authRepository = remember {
        AuthRepository()
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf("")
    }

    var loading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🛡 AegisCall",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Your AI guardian against scam calls",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 32.dp
            )
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                error = ""
            },
            label = {
                Text("Email")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                error = ""
            },
            label = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            singleLine = true
        )

        if (error.isNotEmpty()) {

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Button(
            onClick = {

                if (email.isBlank() || password.isBlank()) {
                    error = "Enter email and password"
                    return@Button
                }

                loading = true

                authRepository.login(
                    email = email,
                    password = password,
                    onSuccess = {
                        loading = false
                        onLoginSuccess()
                    },
                    onError = {
                        loading = false
                        error = it
                    }
                )
            },
            enabled = !loading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {

            Text(
                if (loading) "Signing in..."
                else "LOGIN"
            )
        }

        TextButton(
            onClick = onCreateAccount,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create a new account")
        }
    }
}