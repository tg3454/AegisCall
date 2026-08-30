package com.codegeeks.aegiscall.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codegeeks.aegiscall.R

/**
 * Log-in form. UI only — [onLogIn] and [onForgotPassword] are intentionally unwired.
 */
@Composable
internal fun LoginScreen(
    onLogIn: () -> Unit,
    onForgotPassword: () -> Unit,
    onGoToSignUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var masked by rememberSaveable { mutableStateOf(true) }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.login_headline),
            style = AuthType.Headline,
            color = AuthColors.Parchment,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.login_subhead),
            style = AuthType.Body,
            color = AuthColors.Slate,
        )

        Spacer(Modifier.height(32.dp))

        FieldLabel(stringResource(R.string.auth_email))
        Spacer(Modifier.height(8.dp))
        AuthTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = stringResource(R.string.auth_email_placeholder),
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        )

        Spacer(Modifier.height(20.dp))

        FieldLabel(stringResource(R.string.auth_password))
        Spacer(Modifier.height(8.dp))
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.login_password_placeholder),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            masked = masked,
            trailing = { MaskToggle(masked = masked, onToggle = { masked = !masked }) },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(
                onClick = onForgotPassword,
                colors = ButtonDefaults.textButtonColors(contentColor = AuthColors.Verdigris),
            ) {
                Text(text = stringResource(R.string.login_forgot), style = AuthType.Small)
            }
        }

        Spacer(Modifier.height(16.dp))

        PrimaryAction(text = stringResource(R.string.login_action), onClick = onLogIn)

        Spacer(Modifier.height(8.dp))

        FooterPrompt(
            prompt = stringResource(R.string.login_footer_prompt),
            action = stringResource(R.string.login_footer_action),
            onAction = onGoToSignUp,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(heightDp = 800)
@Composable
private fun LoginScreenPreview() {
    AuthScaffold {
        LoginScreen(onLogIn = {}, onForgotPassword = {}, onGoToSignUp = {})
    }
}
