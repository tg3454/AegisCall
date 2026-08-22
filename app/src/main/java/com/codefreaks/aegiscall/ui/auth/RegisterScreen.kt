package com.codefreaks.aegiscall.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.codefreaks.aegiscall.data.repository.AuthRepository

@Composable
fun RegisterScreen(
    onRegistrationSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {

    val authRepository = remember {
        AuthRepository()
    }

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var role by remember {
        mutableStateOf("GUARDIAN")
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
            text = "Create Account",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                error = ""
            },
            label = {
                Text("Name")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
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

        Text(
            text = "I am a:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 20.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            FilterChip(
                selected = role == "GUARDIAN",
                onClick = {
                    role = "GUARDIAN"
                },
                label = {
                    Text("Guardian")
                }
            )

            FilterChip(
                selected = role == "PROTECTED",
                onClick = {
                    role = "PROTECTED"
                },
                label = {
                    Text("Protected User")
                }
            )
        }

        if (error.isNotEmpty()) {

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Button(
            onClick = {

                if (
                    name.isBlank() ||
                    email.isBlank() ||
                    password.isBlank()
                ) {
                    error = "Fill in all fields"
                    return@Button
                }

                if (password.length < 6) {
                    error = "Password must be at least 6 characters"
                    return@Button
                }

                loading = true

                authRepository.register(
                    name = name,
                    email = email,
                    password = password,
                    role = role,
                    onSuccess = {
                        loading = false
                        onRegistrationSuccess()
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
                .padding(top = 24.dp)
        ) {

            Text(
                if (loading) "Creating..."
                else "CREATE ACCOUNT"
            )
        }

        TextButton(
            onClick = onBackToLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? Login")
        }
    }
}