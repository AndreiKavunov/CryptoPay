package com.example.basicexample.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.unit.dp

@Composable
fun MyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
//    val colors = if (darkTheme) {
//        darkColorScheme
//    } else {
//        lightColorScheme
//    }

    val colors = lightColorScheme

    val additionalColors: AdditionalColors = if(darkTheme) {
//        darkAdditionalColors
        lightAdditionalColors
    } else {
        lightAdditionalColors
    }

    CompositionLocalProvider(LocalAdditionalColors provides additionalColors) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography(),
            shapes = shapes,
            content = content,
        )
    }
}


//
// LIGHT
//

internal val dkt_theme_light_primary = Color(0xFF33FF33)
internal val dkt_theme_light_onPrimary = Color(0xFFFFFFFF)
internal val dkt_theme_light_primaryContainer = Color(0xFFCCE5FF)
internal val dkt_theme_light_onPrimaryContainer = Color(0xFF001D31)

internal val dkt_theme_light_secondary = Color(0xFF51606F)
internal val dkt_theme_light_onSecondary = Color(0xFFFFFFFF)
internal val dkt_theme_light_secondaryContainer = Color(0xFFD4E4F6)
internal val dkt_theme_light_onSecondaryContainer = Color(0xFF0D1D2A)

internal val dkt_theme_light_tertiary = Color(0xFF006497)
internal val dkt_theme_light_onTertiary = Color(0xFFFFFFFF)
internal val dkt_theme_light_tertiaryContainer = Color(0xFFCCE5FF)
internal val dkt_theme_light_onTertiaryContainer = Color(0xFF001D31)

internal val dkt_theme_light_error = Color(0xFFBA1A1A)
internal val dkt_theme_light_onError = Color(0xFFFFFFFF)
internal val dkt_theme_light_errorContainer = Color(0xFFFFDAD6)
internal val dkt_theme_light_onErrorContainer = Color(0xFF410002)

internal val dkt_theme_light_background = Color(0xFFFCFCFF)
internal val dkt_theme_light_onBackground = Color(0xFF1A1C1E)

internal val dkt_theme_light_surface = Color(0xFFFCFCFF)
internal val dkt_theme_light_onSurface = Color(0xFF1A1C1E)
internal val dkt_theme_light_surfaceVariant = Color(0xFFDEE3EB)
internal val dkt_theme_light_onSurfaceVariant = Color(0xFF42474E)
internal val dkt_theme_light_surfaceTint = Color(0xFFFFFFFF) // 0xFF006497

internal val dkt_theme_light_outline = Color(0xFF72787E)
internal val dkt_theme_light_outlineVariant = Color(0xFFC2C7CE)

internal val dkt_theme_light_inverseOnSurface = Color(0xFFF0F0F4)
internal val dkt_theme_light_inverseSurface = Color(0xFF2F3133)
internal val dkt_theme_light_inversePrimary = Color(0xFF92CCFF)

internal val dkt_theme_light_shadow = Color(0xFF000000)
internal val dkt_theme_light_scrim = Color(0xFF000000)

//
// DARK
//

internal val dkt_theme_dark_primary = Color(0xFF33FF33)
internal val dkt_theme_dark_onPrimary = Color(0xFF003351)
internal val dkt_theme_dark_primaryContainer = Color(0xFF004B73)
internal val dkt_theme_dark_onPrimaryContainer = Color(0xFFCCE5FF)

internal val dkt_theme_dark_secondary = Color(0xFFB8C8DA)
internal val dkt_theme_dark_onSecondary = Color(0xFF23323F)
internal val dkt_theme_dark_secondaryContainer = Color(0xFF394857)
internal val dkt_theme_dark_onSecondaryContainer = Color(0xFFD4E4F6)

internal val dkt_theme_dark_tertiary = Color(0xFF9ACBFF)
internal val dkt_theme_dark_onTertiary = Color(0xFF003355)
internal val dkt_theme_dark_tertiaryContainer = Color(0xFF004A79)
internal val dkt_theme_dark_onTertiaryContainer = Color(0xFFCFE5FF)

internal val dkt_theme_dark_error = Color(0xFFFFB4AB)
internal val dkt_theme_dark_errorContainer = Color(0xFF93000A)
internal val dkt_theme_dark_onError = Color(0xFF690005)
internal val dkt_theme_dark_onErrorContainer = Color(0xFFFFDAD6)

internal val dkt_theme_dark_background = Color(0xFF1A1C1E)
internal val dkt_theme_dark_onBackground = Color(0xFFE2E2E5)

internal val dkt_theme_dark_surface = Color(0xFF1A1C1E)
internal val dkt_theme_dark_onSurface = Color(0xFFE2E2E5)
internal val dkt_theme_dark_surfaceVariant = Color(0xFF42474E)
internal val dkt_theme_dark_onSurfaceVariant = Color(0xFFC2C7CE)
internal val dkt_theme_dark_surfaceTint = Color(0xFF92CCFF)

internal val dkt_theme_dark_outline = Color(0xFF8C9198)
internal val dkt_theme_dark_outlineVariant = Color(0xFF42474E)

internal val dkt_theme_dark_inverseOnSurface = Color(0xFF1A1C1E)
internal val dkt_theme_dark_inverseSurface = Color(0xFFE2E2E5)
internal val dkt_theme_dark_inversePrimary = Color(0xFF006497)

internal val dkt_theme_dark_shadow = Color(0xFF000000)
internal val dkt_theme_dark_scrim = Color(0xFF000000)


// Опорный цвет
internal val seed = Color(0xFF007DBC)

// Доп. цвета
internal val yellow = Color(0xFFFFEA28)
internal val orange = Color(0xFFEC6607)
internal val green = Color(0xFF23A942)

data class AdditionalColors(
    val yellow: Color = Color(0xFF695F00),
    val onyellow: Color = Color(0xFFFFFFFF),
    val yellowContainer: Color = Color(0xFFFAE521),
    val onyellowContainer: Color = Color(0xFF201C00),

    val orange: Color = Color(0xFF9F4200),
    val onorange: Color = Color(0xFFFFFFFF),
    val orangeContainer: Color = Color(0xFFFFDBCB),
    val onorangeContainer: Color = Color(0xFF341100),

    val green: Color = Color(0xFF006E23),
    val ongreen: Color = Color(0xFFFFFFFF),
    val greenContainer: Color = Color(0xFF7FFD89),
    val ongreenContainer: Color = Color(0xFF002106),
)

internal val lightAdditionalColors = AdditionalColors(
    yellow = Color(0xFF695F00),
    onyellow = Color(0xFFFFFFFF),
    yellowContainer = Color(0xFFFAE521),
    onyellowContainer = Color(0xFF201C00),

    orange = Color(0xFF9F4200),
    onorange = Color(0xFFFFFFFF),
    orangeContainer = Color(0xFFFFDBCB),
    onorangeContainer = Color(0xFF341100),

    green = Color(0xFF006E23),
    ongreen = Color(0xFFFFFFFF),
    greenContainer = Color(0xFF7FFD89),
    ongreenContainer = Color(0xFF002106),
)

internal val darkAdditionalColors = AdditionalColors(
    yellow = Color(0xFFDCC900),
    onyellow = Color(0xFF363100),
    yellowContainer = Color(0xFF4F4800),
    onyellowContainer = Color(0xFFFAE521),

    orange = Color(0xFFFFB692),
    onorange = Color(0xFF562000),
    orangeContainer = Color(0xFF793000),
    onorangeContainer = Color(0xFFFFDBCB),

    green = Color(0xFF62DF70),
    ongreen = Color(0xFF00390E),
    greenContainer = Color(0xFF005319),
    ongreenContainer = Color(0xFF7FFD89),
)

val LocalAdditionalColors = compositionLocalOf { AdditionalColors() }

internal val lightColorScheme = lightColorScheme(
    primary = dkt_theme_light_primary,
    onPrimary = dkt_theme_light_onPrimary,
    primaryContainer = dkt_theme_light_primaryContainer,
    onPrimaryContainer = dkt_theme_light_onPrimaryContainer,

    inversePrimary = dkt_theme_light_inversePrimary,

    secondary = dkt_theme_light_secondary,
    onSecondary = dkt_theme_light_onSecondary,
    secondaryContainer = dkt_theme_light_secondaryContainer,
    onSecondaryContainer = dkt_theme_light_onSecondaryContainer,

    tertiary = dkt_theme_light_tertiary,
    onTertiary = dkt_theme_light_onTertiary,
    tertiaryContainer = dkt_theme_light_tertiaryContainer,
    onTertiaryContainer = dkt_theme_light_onTertiaryContainer,

    error = dkt_theme_light_error,
    errorContainer = dkt_theme_light_errorContainer,
    onError = dkt_theme_light_onError,
    onErrorContainer = dkt_theme_light_onErrorContainer,

    background = dkt_theme_light_background,
    onBackground = dkt_theme_light_onBackground,

    surface = dkt_theme_light_surface,
    onSurface = dkt_theme_light_onSurface,
    surfaceVariant = dkt_theme_light_surfaceVariant,
    onSurfaceVariant = dkt_theme_light_onSurfaceVariant,
    surfaceTint = dkt_theme_light_surfaceTint,
    inverseOnSurface = dkt_theme_light_inverseOnSurface,
    inverseSurface = dkt_theme_light_inverseSurface,

    outline = dkt_theme_light_outline,
    outlineVariant = dkt_theme_light_outlineVariant,

    scrim = dkt_theme_light_scrim,
)


internal val darkColorScheme = darkColorScheme(
    primary = dkt_theme_dark_primary,
    onPrimary = dkt_theme_dark_onPrimary,
    primaryContainer = dkt_theme_dark_primaryContainer,
    onPrimaryContainer = dkt_theme_dark_onPrimaryContainer,

    secondary = dkt_theme_dark_secondary,
    onSecondary = dkt_theme_dark_onSecondary,
    secondaryContainer = dkt_theme_dark_secondaryContainer,
    onSecondaryContainer = dkt_theme_dark_onSecondaryContainer,

    tertiary = dkt_theme_dark_tertiary,
    onTertiary = dkt_theme_dark_onTertiary,
    tertiaryContainer = dkt_theme_dark_tertiaryContainer,
    onTertiaryContainer = dkt_theme_dark_onTertiaryContainer,

    error = dkt_theme_dark_error,
    errorContainer = dkt_theme_dark_errorContainer,
    onError = dkt_theme_dark_onError,
    onErrorContainer = dkt_theme_dark_onErrorContainer,

    background = dkt_theme_dark_background,
    onBackground = dkt_theme_dark_onBackground,

    surface = dkt_theme_dark_surface,
    onSurface = dkt_theme_dark_onSurface,
    surfaceVariant = dkt_theme_dark_surfaceVariant,
    onSurfaceVariant = dkt_theme_dark_onSurfaceVariant,
    surfaceTint = dkt_theme_dark_surfaceTint,
    inverseOnSurface = dkt_theme_dark_inverseOnSurface,
    inverseSurface = dkt_theme_dark_inverseSurface,

    outline = dkt_theme_dark_outline,
    outlineVariant = dkt_theme_dark_outlineVariant,

    inversePrimary = dkt_theme_dark_inversePrimary,

    scrim = dkt_theme_dark_scrim,
)
internal val shapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(0.dp),
    extraLarge = RoundedCornerShape(0.dp)
)
