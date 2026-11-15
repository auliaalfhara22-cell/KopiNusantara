package com.example.kopinusantara.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val Espresso = Color(0xFF3B2314)
val CoffeeBrown = Color(0xFF6F4E37)
val Caramel = Color(0xFFC68B59)
val Cream = Color(0xFFFBF3E8)
val Latte = Color(0xFFF1E0CC)

private val CoffeeColors = lightColorScheme(
    primary = CoffeeBrown,
    onPrimary = Color.White,
    primaryContainer = Latte,
    onPrimaryContainer = Espresso,
    secondary = Caramel,
    onSecondary = Color.White,
    background = Cream,
    surface = Cream,
    surfaceVariant = Latte,
    onBackground = Espresso,
    onSurface = Espresso,
    outline = Color(0xFFB89F8A),
    error = Color(0xFFB3261E)
)

// Judul memakai font Serif supaya terasa seperti kafe, bukan tampilan standar
private val AppTypography = Typography.copy(
    headlineLarge = Typography.headlineLarge.copy(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold),
    headlineMedium = Typography.headlineMedium.copy(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold),
    headlineSmall = Typography.headlineSmall.copy(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold),
    titleLarge = Typography.titleLarge.copy(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold),
    titleMedium = Typography.titleMedium.copy(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
)

@Composable
fun KopiNusantaraTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CoffeeColors,
        typography = AppTypography,
        content = content
    )
}
