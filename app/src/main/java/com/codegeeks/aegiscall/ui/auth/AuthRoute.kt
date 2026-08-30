package com.codegeeks.aegiscall.ui.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

private enum class AuthForm { LogIn, SignUp }

/**
 * Hosts the two auth forms. Kept to plain local state rather than a navigation library: the crest
 * and wordmark in [AuthScaffold] stay put and only the form beneath them swaps.
 *
 * This is UI only. Nothing submits, validates, or authenticates.
 */
@Composable
fun AuthRoute(modifier: Modifier = Modifier) {
    var form by rememberSaveable { mutableStateOf(AuthForm.LogIn) }

    AuthScaffold(modifier = modifier) {
        AnimatedContent(
            targetState = form,
            transitionSpec = {
                val direction = if (targetState == AuthForm.SignUp) 1 else -1
                (slideInHorizontally { width -> direction * width / 6 } + fadeIn(tween(220)))
                    .togetherWith(
                        slideOutHorizontally { width -> -direction * width / 6 } +
                            fadeOut(tween(160)),
                    )
            },
            label = "auth-form",
        ) { target ->
            when (target) {
                AuthForm.LogIn -> LoginScreen(
                    onLogIn = {},
                    onForgotPassword = {},
                    onGoToSignUp = { form = AuthForm.SignUp },
                )

                AuthForm.SignUp -> SignUpScreen(
                    onCreateAccount = {},
                    onGoToLogIn = { form = AuthForm.LogIn },
                )
            }
        }
    }
}

@Preview(heightDp = 800)
@Composable
private fun AuthRoutePreview() {
    AuthRoute()
}
