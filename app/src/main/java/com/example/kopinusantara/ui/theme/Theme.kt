package com.example.kopinusantara.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CoffeeColors = lightColorScheme(
    primary = Color(0xFF6F4E37),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDCC2),
    onPrimaryContainer = Color(0xFF2B1B12),
    secondary = Color(0xFFC68B59),
    background = Color(0xFFFFF8F0),
    surface = Color(0xFFFFF8F0),
    surfaceVariant = Color(0xFFFFEDE0),
    onBackground = Color(0xFF2B1B12),
    onSurface = Color(0xFF2B1B12),
    error = Color(0xFFB3261E)
)

@Composable
fun KopiNusantaraTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CoffeeColors,
        typography = Typography,
        content = content
    )
}