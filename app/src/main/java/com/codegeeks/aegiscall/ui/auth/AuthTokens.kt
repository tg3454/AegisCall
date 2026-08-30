package com.codegeeks.aegiscall.ui.auth

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * The auth screens are brand-locked: they render identically in light and dark mode, because the
 * crest should read the same either way. They deliberately do not pull colours from MaterialTheme,
 * which is wallpaper-derived (dynamic colour) for the rest of the app.
 *
 * Palette is taken from the aegis itself — a bronze-rimmed shield: oxidised bronze for actions,
 * bronze for the rim, parchment on a green-undertoned ink ground.
 */
internal object AuthColors {
    /** Page ground. Near-black with a green undertone. */
    val Ink = Color(0xFF0F1513)

    /** Field and input fill, one step off the ground. */
    val InkRaised = Color(0xFF161E1B)

    /** Oxidised bronze. The only colour that means "you can act here". */
    val Verdigris = Color(0xFF5FBFA3)

    /** Resting borders and unchecked controls. */
    val VerdigrisDim = Color(0xFF2F5D51)

    /** The shield rim, and the wordmark. Used sparingly. */
    val Bronze = Color(0xFFC9A063)

    /** Primary text. */
    val Parchment = Color(0xFFECE6DA)

    /** Secondary text, placeholders, labels. */
    val Slate = Color(0xFF8B978F)
}

internal object AuthType {
    /** Monospace, widely tracked. Reserved for the wordmark. */
    val Wordmark = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        letterSpacing = 4.sp,
    )

    /** Monospace micro-label. Reserved for field labels and the mask toggle. */
    val Label = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        letterSpacing = 1.5.sp,
    )

    val Headline = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.5).sp,
    )

    val Body = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 15.sp,
        lineHeight = 22.sp,
    )

    val Field = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
    )

    val Action = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    )

    val Small = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 13.sp,
        lineHeight = 18.sp,
    )
}
