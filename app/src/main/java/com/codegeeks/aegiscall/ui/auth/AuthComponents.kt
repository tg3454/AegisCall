package com.codegeeks.aegiscall.ui.auth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import com.codegeeks.aegiscall.R

/**
 * The signature mark: a heater shield with a bronze hairline rim and a verdigris chevron that
 * repeats the shield's own point. Decorative, so it carries no content description.
 */
@Composable
internal fun ShieldCrest(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(width = 56.dp, height = 64.dp)) {
        val w = size.width
        val h = size.height

        val shield = Path().apply {
            moveTo(w * 0.5f, 0f)
            lineTo(w, h * 0.16f)
            lineTo(w, h * 0.52f)
            cubicTo(w, h * 0.80f, w * 0.76f, h * 0.94f, w * 0.5f, h)
            cubicTo(w * 0.24f, h * 0.94f, 0f, h * 0.80f, 0f, h * 0.52f)
            lineTo(0f, h * 0.16f)
            close()
        }
        drawPath(
            path = shield,
            brush = Brush.verticalGradient(
                listOf(AuthColors.InkRaised, AuthColors.VerdigrisDim),
            ),
        )
        drawPath(
            path = shield,
            color = AuthColors.Bronze,
            style = Stroke(width = 1.5.dp.toPx()),
        )

        val chevron = Path().apply {
            moveTo(w * 0.30f, h * 0.40f)
            lineTo(w * 0.50f, h * 0.60f)
            lineTo(w * 0.70f, h * 0.40f)
        }
        drawPath(
            path = chevron,
            color = AuthColors.Verdigris,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            ),
        )
    }
}

/**
 * Shared frame for both auth screens. The crest and wordmark live here, so switching between
 * logging in and signing up leaves them untouched.
 */
@Composable
internal fun AuthScaffold(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AuthColors.Ink),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                // safeDrawing covers system bars, cutout and the keyboard.
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
        ) {
            Spacer(Modifier.height(48.dp))
            ShieldCrest()
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.auth_wordmark),
                style = AuthType.Wordmark,
                color = AuthColors.Bronze,
            )
            Spacer(Modifier.height(28.dp))
            content()
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
internal fun FieldLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        style = AuthType.Label,
        color = AuthColors.Slate,
        modifier = modifier,
    )
}

@Composable
internal fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    modifier: Modifier = Modifier,
    masked: Boolean = false,
    trailing: (@Composable () -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = AuthType.Field,
        placeholder = { Text(text = placeholder, style = AuthType.Field) },
        trailingIcon = trailing,
        visualTransformation =
            if (masked) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = AuthColors.Parchment,
            unfocusedTextColor = AuthColors.Parchment,
            focusedContainerColor = AuthColors.InkRaised,
            unfocusedContainerColor = AuthColors.InkRaised,
            cursorColor = AuthColors.Verdigris,
            focusedBorderColor = AuthColors.Verdigris,
            unfocusedBorderColor = AuthColors.VerdigrisDim,
            focusedPlaceholderColor = AuthColors.Slate,
            unfocusedPlaceholderColor = AuthColors.Slate,
        ),
    )
}

/** Reveals or hides a password. Labelled with words rather than an eye, so its state is explicit. */
@Composable
internal fun MaskToggle(masked: Boolean, onToggle: () -> Unit) {
    TextButton(
        onClick = onToggle,
        colors = ButtonDefaults.textButtonColors(contentColor = AuthColors.Verdigris),
    ) {
        Text(
            text = stringResource(if (masked) R.string.auth_show else R.string.auth_hide),
            style = AuthType.Label,
        )
    }
}

@Composable
internal fun PrimaryAction(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AuthColors.Verdigris,
            contentColor = AuthColors.Ink,
        ),
    ) {
        Text(text = text, style = AuthType.Action)
    }
}

@Composable
internal fun FooterPrompt(
    prompt: String,
    action: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(text = prompt, style = AuthType.Small, color = AuthColors.Slate)
        TextButton(
            onClick = onAction,
            colors = ButtonDefaults.textButtonColors(contentColor = AuthColors.Verdigris),
        ) {
            Text(
                text = action,
                style = AuthType.Small.copy(fontWeight = FontWeight.SemiBold),
            )
        }
    }
}
