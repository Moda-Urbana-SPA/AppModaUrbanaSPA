package com.example.modaurbanaspa.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val light = lightColorScheme(
    primary = Black, onPrimary = White,
    background = White, onBackground = Black,
    surface = White, onSurface = Black,
)
private val dark = darkColorScheme(
    primary = White, onPrimary = Black,
    background = Black, onBackground = White,
    surface = Black, onSurface = White,
)
@Composable
fun ModaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit){
    MaterialTheme(colorScheme = if (darkTheme) dark else light, typography = Typography, content = content)
}