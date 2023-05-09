package com.example.amyswateringapp.common.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    secondary = Color(0xFFC5DCC2),
    tertiary = Color(0xFFEFB8C8)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF216c2e),
    onPrimary= Color(0xFFffffff),
    primaryContainer= Color(0xFFa7f5a7),
    onPrimaryContainer= Color(0xFF002106),
    secondary = Color(0xFFffffff),
    onSecondary= Color(0xFFffffff),
    secondaryContainer= Color(0xFFd5e8d0),
    onSecondaryContainer= Color(0xFF101f10),
    tertiary= Color(0xFF39656b),
    onTertiary= Color(0xFFffffff),
    tertiaryContainer= Color(0xFFbcebf2),
    onTertiaryContainer= Color(0xFF001f23),
    background= Color(0xFFfcfdf7),
    onBackground= Color(0xFF1a1c19),
    surface= Color(0xFFfcfdf7),
    onSurface= Color(0xFF1a1c19),
    surfaceVariant= Color(0xFFdee5d9),
    onSurfaceVariant= Color(0xFF424940),
    error= Color(0xFFba1a1a),
    onError= Color(0xFFffffff),
    errorContainer= Color(0xFFffdad6),
    onErrorContainer= Color(0xFF410002),
    outline= Color(0xFF72796f),
)



@Composable
fun AmysWateringAppTheme(
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }
    //its not using the custom padding initialised here


    CompositionLocalProvider(LocalPadding provides customPadding) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

@Immutable
data class CustomPadding(val card :Dp, val default: Dp, val lazyList: Dp)

private val customPadding = CustomPadding(card = 10.dp, default = 0.dp, lazyList = 8.dp)

private val LocalPadding = compositionLocalOf { customPadding }

val MaterialTheme.padding: CustomPadding
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current

@Preview
@Composable
fun Colours() {
    AmysWateringAppTheme() {
        val colourList = listOf<Color>(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.onSecondary, //5
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.onTertiary,
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.onTertiaryContainer, //10
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.onBackground,
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.onSurface,
            MaterialTheme.colorScheme.surfaceVariant, //15
            MaterialTheme.colorScheme.onSurfaceVariant,
            MaterialTheme.colorScheme.error,
            MaterialTheme.colorScheme.onError,
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer, //20
            MaterialTheme.colorScheme.outline,
            MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp),
            MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
            MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp), //25
            MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
            MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp),
        )
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {


            itemsIndexed(colourList){ index, materialColor ->
                Box(Modifier.size(85.dp).background(color = materialColor)){Text(index.toString())} }
        }
    }
}
