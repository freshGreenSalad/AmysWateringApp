package com.example.amyswateringapp.features.introduction.presentation

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import com.example.amyswateringapp.features.introduction.domain.InOrOutScreenStates
import kotlinx.coroutines.launch

@Composable
fun LeftRightDragAnimation(
    color: Color,
    goIn: State<InOrOutScreenStates>,
    xOffset: Float,
    reverse: Float,
    text: String,
    animateableShape: @Composable (color: Color,
                                   animateHeightInternal: Animatable<Float, AnimationVector1D>,
                                   animateWidthInternal: Animatable<Float, AnimationVector1D>,
                                   xOffset:Float,
                                   goIn: State<InOrOutScreenStates>,
                                   text: String)->Unit
){
    val animateXOffset = remember{ Animatable(0f) }
    val animateYOffset = remember{ Animatable(400f) }
    val durationMillis = 700

    LaunchedEffect(key1 = goIn.value) {
        if (goIn.value.moveToStartPosition) {
            launch {
                animateXOffset.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = durationMillis))
            }
            launch {
                animateYOffset.animateTo(reverse* 400f, animationSpec = tween(easing = EaseOutQuad, durationMillis = durationMillis))
            }
        }
        if (goIn.value.moveIn) {
            launch {
                animateXOffset.animateTo(-xOffset, animationSpec = tween(durationMillis = durationMillis))
            }
            launch {
                animateYOffset.animateTo(0f, animationSpec = tween(easing = EaseOutQuad, durationMillis = durationMillis))
            }
        }
        if (goIn.value.moveOut){
            launch {
                animateXOffset.animateTo(-2*xOffset, animationSpec = tween( durationMillis = durationMillis))
            }
            launch {
                animateYOffset.animateTo(reverse *400f, animationSpec = tween(easing = EaseOutQuad, durationMillis = durationMillis))
            }
        }
    }
    animateableShape(color, animateYOffset, animateXOffset, xOffset, goIn, text)
}

@Composable
fun AnimateableCircle(
    color: Color,
    animateHeightInternal: Animatable<Float, AnimationVector1D>,
    animateWidthInternal: Animatable<Float, AnimationVector1D>,
    xOffset:Float,
    goIn: State<InOrOutScreenStates>,
    text: String
) {
    WhiteBackingCircle(animateHeightInternal, animateWidthInternal, xOffset)
    RedCirleWithText(color, animateHeightInternal, animateWidthInternal, xOffset, goIn, text)
}

@Composable
fun WhiteBackingCircle(
    animateHeightInternal: Animatable<Float, AnimationVector1D>,
    animateWidthInternal: Animatable<Float, AnimationVector1D>,
    xOffset:Float,
) {

    Box(modifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxSize()
        .graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
        }
        .drawWithCache {
            onDrawWithContent {
                drawCircle(
                    color = Color.White,
                    radius = drawContext.size.width.coerceAtMost(drawContext.size.height) / 2 - 30,
                    center = Offset(
                        x = center.x + animateWidthInternal.value + xOffset,
                        y = center.y + animateHeightInternal.value
                    ),
                    blendMode = BlendMode.DstAtop
                )
                drawContent()
            }
        },
    )
}

@Composable
fun RedCirleWithText(
    color: Color,
    animateHeightInternal: Animatable<Float, AnimationVector1D>,
    animateWidthInternal: Animatable<Float, AnimationVector1D>,
    xOffset:Float,
    goIn: State<InOrOutScreenStates>,
    text: String
) {

    val textMeasurer = rememberTextMeasurer()
    val typography = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center)
    Box(modifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxSize()
        .graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
        }
        .drawWithCache {

            val graphicsText = textMeasurer.measure(
                text = text,
                style = typography,
                constraints = Constraints.fixedWidth( if ( size.width > size.height ) {
                    size.height.toInt() - 100
                } else {
                    size.width.toInt() - 120
                })
            )

            onDrawWithContent {
                drawCircle(
                    color = color,
                    radius = this@drawWithCache.size.width.coerceAtMost(this@drawWithCache.size.height) / 2 -30,
                    center = Offset(
                        x = center.x + animateWidthInternal.value + xOffset,
                        y = center.y + animateHeightInternal.value
                    )
                )
                if (goIn.value.moveIn) {
                    drawText(
                        textLayoutResult = graphicsText,
                        topLeft = Offset(
                            x = center.x - graphicsText.size.width / 2,
                            y = center.y - graphicsText.size.height / 2
                        ),
                        blendMode = BlendMode.DstOut
                    )
                }
                drawContent()
            }
        },
    )
}