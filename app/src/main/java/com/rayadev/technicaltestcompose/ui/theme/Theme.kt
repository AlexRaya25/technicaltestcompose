package com.rayadev.technicaltestcompose.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),          // Soft purple for primary elements
    onPrimary = Color.Black,               // Black text/icons on primary background
    primaryContainer = Color(0xFF3700B3),  // Darker purple for primary containers
    secondary = Color(0xFF03DAC6),         // Teal accent
    onSecondary = Color.Black,             // Black text/icons on secondary background
    background = Color(0xFF121212),        // Dark background for dark theme
    onBackground = Color(0xFFE0E0E0),      // Light text on dark background
    surface = Color(0xFF1E1E1E),           // Dark surface for cards
    onSurface = Color(0xFFE0E0E0),         // Light text/icons on surface
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),        // Profundo y vibrante
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC5),      // Suave para los botones de acción secundaria
    onSecondary = Color.Black,
    background = Color(0xFFF6F6F6),     // Fondo claro y cálido
    onBackground = Color(0xFF1C1B1F),   // Texto oscuro para legibilidad
    surface = Color(0xFFFFFFFF),        // Superficies limpias y contrastadas
    onSurface = Color(0xFF333333),      // Texto en superficie
    tertiary = Color(0xFFFFA726),       // Acento para algunos íconos o detalles
    onTertiary = Color.Black,
)

@Composable
fun TechnicaltestcomposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
