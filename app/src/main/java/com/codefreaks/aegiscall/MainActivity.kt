package com.codefreaks.aegiscall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.codefreaks.aegiscall.ui.auth.LoginScreen
import com.codefreaks.aegiscall.ui.auth.RegisterScreen
import com.codefreaks.aegiscall.ui.guardian.GuardianHomeScreen
import com.codefreaks.aegiscall.ui.protected.CallScreen
import com.codefreaks.aegiscall.ui.protected.ProtectedHomeScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AegisCallApp()
        }
    }
}
fun loadUserRole(
    onRoleLoaded: (String) -> Unit,
    onError: (String) -> Unit
) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    if (uid == null) {
        onError("User is not logged in")
        return
    }

    FirebaseFirestore.getInstance()
        .collection("users")
        .document(uid)
        .get()
        .addOnSuccessListener { document ->

            val role = document.getString("role")

            if (role != null) {
                onRoleLoaded(role)
            } else {
                onError("User role not found")
            }
        }
        .addOnFailureListener {
            onError(it.message ?: "Failed to load user")
        }
}
@Composable
fun AegisCallApp() {

    var screen by remember {
        mutableStateOf("login")
    }

    when (screen) {

        "login" -> {
            LoginScreen(
                onLoginSuccess = {
                    loadUserRole(
                        onRoleLoaded = { role ->
                            screen = when (role) {
                                "GUARDIAN" -> "guardian"
                                "PROTECTED" -> "protected"
                                else -> "login"
                            }
                        },
                        onError = {
                            // show error
                        }
                    )
                },
                onCreateAccount = {
                    screen = "register"
                }
            )
        }

        "register" -> {
            RegisterScreen(
                onRegistrationSuccess = {
                    loadUserRole(
                        onRoleLoaded = { role ->
                            screen = when (role) {
                                "GUARDIAN" -> "guardian"
                                "PROTECTED" -> "protected"
                                else -> "login"
                            }
                        },
                        onError = {
                            // show error
                        }
                    )
                },
                onBackToLogin = {
                    screen = "login"
                }
            )
        }

        "guardian" -> {
            GuardianHomeScreen(
                onViewAlert = {
                    // next
                }
            )
        }

        "protected" -> {
            ProtectedHomeScreen(
                onStartCall = {
                    screen = "call"
                }
            )
        }

        "call" -> {
            CallScreen(
                onEndCall = {
                    screen = "protected"
                }
            )
        }
    }
}