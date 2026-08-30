package com.codegeeks.aegiscall.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codegeeks.aegiscall.R

/**
 * Sign-up form. UI only — [onCreateAccount] is intentionally unwired, and nothing here validates
 * the email, the password length, or whether the two passwords match.
 */
@Composable
internal fun SignUpScreen(
    onCreateAccount: () -> Unit,
    onGoToLogIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var masked by rememberSaveable { mutableStateOf(true) }
    var agreedToTerms by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.signup_headline),
            style = AuthType.Headline,
            color = AuthColors.Parchment,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.signup_subhead),
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
            placeholder = stringResource(R.string.signup_password_placeholder),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
            masked = masked,
            trailing = { MaskToggle(masked = masked, onToggle = { masked = !masked }) },
        )

        Spacer(Modifier.height(20.dp))

        FieldLabel(stringResource(R.string.signup_confirm))
        Spacer(Modifier.height(8.dp))
        AuthTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = stringResource(R.string.signup_confirm_placeholder),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            masked = masked,
        )

        Spacer(Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = agreedToTerms,
                onCheckedChange = { agreedToTerms = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = AuthColors.Verdigris,
                    uncheckedColor = AuthColors.VerdigrisDim,
                    checkmarkColor = AuthColors.Ink,
                ),
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.signup_terms),
                style = AuthType.Small,
                color = AuthColors.Slate,
            )
        }

        Spacer(Modifier.height(20.dp))

        PrimaryAction(text = stringResource(R.string.signup_action), onClick = onCreateAccount)

        Spacer(Modifier.height(8.dp))

        FooterPrompt(
            prompt = stringResource(R.string.signup_footer_prompt),
            action = stringResource(R.string.signup_footer_action),
            onAction = onGoToLogIn,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(heightDp = 900)
@Composable
private fun SignUpScreenPreview() {
    AuthScaffold {
        SignUpScreen(onCreateAccount = {}, onGoToLogIn = {})
    }
}
