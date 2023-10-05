package com.example.amyswateringapp.introduction.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.amyswateringapp.introduction.domain.InOrOutScreenStates
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
                animateXOffset.animateTo(-1250f, animationSpec = tween(durationMillis = durationMillis))
            }
            launch {
                animateYOffset.animateTo(0f, animationSpec = tween(easing = EaseOutQuad, durationMillis = durationMillis))
            }
        }
        if (goIn.value.moveOut){
            launch {
                animateXOffset.animateTo(-2500f, animationSpec = tween( durationMillis = durationMillis))
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

    val textEnterDelay = 400

    Box(modifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxSize()
        .drawWithContent {
            drawCircle(
                color = color,
                radius = drawContext.size.width / 2 - 30,
                center = Offset(
                    (center.x + animateWidthInternal.value + xOffset),
                    center.y + animateHeightInternal.value
                )
            )
            drawContent()
        },
        contentAlignment = Alignment.Center
    ){
        AnimatedVisibility (
            visible = goIn.value.moveIn,
            enter = slideInHorizontally(animationSpec = tween(delayMillis = textEnterDelay)) + fadeIn(animationSpec = tween(delayMillis = textEnterDelay, durationMillis = 1)),
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 100))
        ) {
            Text(
                modifier = Modifier.width(350.dp),
                text = text,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}