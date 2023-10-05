package com.example.amyswateringapp.common.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit

/**
 * @param fontSize the shimmer width of the animation is the Font size * 2
 */
@Composable
fun animatedBrush(
    fontSize: TextUnit
): Brush {

    val currentFontSizePx = with(LocalDensity.current) {fontSize.toPx()}

    val currentFontSizeDoublePx = currentFontSizePx * 2

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = currentFontSizeDoublePx,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing)
            ),
        label = "Brush Animation"
    )

    val shimmerColours = listOf(
        Color.White,
        Color.LightGray.copy(alpha = 0.6f),
        Color.White
    )

    return  Brush.linearGradient(
        colors = shimmerColours,
        start = Offset(offset, offset),
        end = Offset(offset + currentFontSizePx, offset + currentFontSizePx),
        tileMode = TileMode.Mirror
    )
}