package com.example.binarybrainz.ui.theme

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

// Define los tonos de gris
val DarkGrey = Color(0xFF2C2C2C)  // Gris oscuro
val Grey = Color(0xFF808080)       // Gris intermedio
val LightGrey = Color(0xFFD3D3D3)  // Gris claro

// Esquema de colores oscuros
private val DarkColorScheme = darkColorScheme(
    primary = DarkGrey,
    onPrimary = Color.White,
    secondary = Grey,
    tertiary = LightGrey
)

// Esquema de colores claros
private val LightColorScheme = lightColorScheme(
    primary = LightGrey,
    onPrimary = Color.Black,
    secondary = Grey,
    tertiary = DarkGrey
)

@Composable
fun BinaryBrainzTheme(
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
