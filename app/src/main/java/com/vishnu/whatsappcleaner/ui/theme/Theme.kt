package com.vishnu.whatsappcleaner.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.vishnu.whatsappcleaner.R

private val DarkColorScheme = darkColorScheme(
    primary = primaryColor,
    secondary = secondaryColor,
    tertiary = tertiaryColor
)

private val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    secondary = secondaryColor,
    tertiary = tertiaryColor
)

private val fontFamily = FontFamily(Font(R.font.geist))

@Composable
fun WhatsAppCleanerTheme(
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

    val Typography = Typography(
        displayLarge = Typography.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = Typography.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = Typography.displaySmall.copy(fontFamily = fontFamily),

        headlineLarge = Typography.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = Typography.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = Typography.headlineSmall.copy(fontFamily = fontFamily),

        titleLarge = Typography.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = Typography.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = Typography.titleSmall.copy(fontFamily = fontFamily),

        bodyLarge = Typography.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = Typography.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = Typography.bodySmall.copy(fontFamily = fontFamily),

        labelLarge = Typography.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = Typography.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = Typography.labelSmall.copy(fontFamily = fontFamily)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}