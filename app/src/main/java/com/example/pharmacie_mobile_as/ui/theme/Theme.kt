package com.example.pharmacie_mobile_as.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF006A6A),
    onPrimary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
    primaryContainer = androidx.compose.ui.graphics.Color(0xFF6FF7F7),
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF002020),
    secondary = androidx.compose.ui.graphics.Color(0xFF4A6363),
    onSecondary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
    secondaryContainer = androidx.compose.ui.graphics.Color(0xCCE8E8),
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xFF051F1F),
    tertiary = androidx.compose.ui.graphics.Color(0xFF4B607C),
    onTertiary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
    tertiaryContainer = androidx.compose.ui.graphics.Color(0xFFD3E4FF),
    onTertiaryContainer = androidx.compose.ui.graphics.Color(0xFF041C35),
    error = androidx.compose.ui.graphics.Color(0xFFBA1A1A),
    errorContainer = androidx.compose.ui.graphics.Color(0xFFFFDAD6),
    onError = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
    onErrorContainer = androidx.compose.ui.graphics.Color(0xFF410002),
    background = androidx.compose.ui.graphics.Color(0xFFFAFDFC),
    onBackground = androidx.compose.ui.graphics.Color(0xFF191C1C),
    surface = androidx.compose.ui.graphics.Color(0xFFFAFDFC),
    onSurface = androidx.compose.ui.graphics.Color(0xFF191C1C)
)

private val DarkColorScheme = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF4DDADA),
    onPrimary = androidx.compose.ui.graphics.Color(0xFF003737),
    primaryContainer = androidx.compose.ui.graphics.Color(0xFF004F4F),
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF6FF7F7),
    secondary = androidx.compose.ui.graphics.Color(0xFFB0CCCC),
    onSecondary = androidx.compose.ui.graphics.Color(0xFF1B3434),
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFF324B4B),
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xCCE8E8),
    tertiary = androidx.compose.ui.graphics.Color(0xFFB3C8E8),
    onTertiary = androidx.compose.ui.graphics.Color(0xFF1C314B),
    tertiaryContainer = androidx.compose.ui.graphics.Color(0xFF344863),
    onTertiaryContainer = androidx.compose.ui.graphics.Color(0xFFD3E4FF),
    error = androidx.compose.ui.graphics.Color(0xFFFFB4AB),
    errorContainer = androidx.compose.ui.graphics.Color(0xFF93000A),
    onError = androidx.compose.ui.graphics.Color(0xFF690005),
    onErrorContainer = androidx.compose.ui.graphics.Color(0xFFFFDAD6),
    background = androidx.compose.ui.graphics.Color(0xFF191C1C),
    onBackground = androidx.compose.ui.graphics.Color(0xFFE0E3E3),
    surface = androidx.compose.ui.graphics.Color(0xFF191C1C),
    onSurface = androidx.compose.ui.graphics.Color(0xFFE0E3E3)
)

@Composable
fun PharmacieTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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
        typography = Typography(),
        content = content
    )
}