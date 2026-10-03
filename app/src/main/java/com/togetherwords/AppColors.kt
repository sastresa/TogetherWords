package com.togetherwords

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val Ink = Color(0xFF243C43)
internal val Background = Color(0xFFF7F6F0)
internal val Mint = Color(0xFFD9EEE5)
internal val Peach = Color(0xFFF6E4D5)
internal val Blue = Color(0xFFDEEAF2)
internal val Lavender = Color(0xFFEAE3F1)
internal val Teal = Color(0xFF275B61)

internal val AppColorScheme =
    lightColorScheme(
        primary = Teal,
        onPrimary = Color.White,
        onSurface = Ink,
        background = Background,
        surface = Color.White,
    )
