package com.example.amyswateringapp.wateringAppScreen


import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.amyswateringapp.ui.theme.AmysWateringAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun graphics() {
    val scope = rememberCoroutineScope()

    val state = remember{mutableStateOf(0f)}
    val rpf  = animateFloatAsState( // rp stands for rainPosition
        targetValue = state.value,
        animationSpec =  tween(1200, easing = EaseIn),
        )
    val rps  = animateFloatAsState( // rp stands for rainPosition
        targetValue = state.value,
        animationSpec =  tween(1500, easing = EaseInOutQuad),
        )

    val blue = Color(0xFFd4e3ff)
    val dBlue = Color(0xFFd8e3f8)
    val list: MutableList<Rain> = remember { mutableListOf<Rain>() }

    repeat(75){
        val y = Random.nextFloat()
        val x = Random.nextFloat()
        val color = if (Random.nextBoolean()){blue}else{dBlue}
        val anim = if (Random.nextBoolean()){rpf}else{rps}
        list.add(Rain(anim, color, y,x))
    }

    Box(
        Modifier
            .fillMaxSize()
            .semantics { testTag = "cloud" }
            .drawWithContent {
                drawContent()
                list.forEach {rain ->
                    drawRain(rain.rpf, rain.color, rain.y, rain.x)
                }
            }
    ) {
        LaunchedEffect(key1 = true) {
            scope.launch {
                delay(50)
                state.value = 1f
            }
        }
    }
}

fun DrawScope.drawRain(rps: State<Float>, color: Color, y:Float, x: Float ){
    val ogw = this.size.width
    val ogh = this.size.height

    val multiplyer = 2f

    val w = ogw * multiplyer
    val h = ogh * multiplyer
    val rp = rps.value

    val adjustLeft = ogw * ((multiplyer-1)/2)
    val adjustUp = ogh * ((multiplyer-1)/2)

    drawLine(
        color = color,
        start = Offset(w * ( rp*.5f + x) - adjustLeft, h * (rp + y) -500f - adjustUp),
        end = Offset(  w * ( rp*.5f  + x) + 20f- adjustLeft,h * (rp  + y) -410f- adjustUp),
        strokeWidth = 20f,
        cap = StrokeCap.Round
    )
}

data class Rain(
    val rpf: State<Float>,
    val color: Color,
    val y:Float,
    val x: Float
)

@Preview
@Composable
fun previewGraphics() {
    AmysWateringAppTheme {
        graphics()
    }
}