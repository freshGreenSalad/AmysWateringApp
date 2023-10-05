package com.example.amyswateringapp.introduction.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amyswateringapp.R
import com.example.amyswateringapp.common.presentation.animatedBrush


@Composable
fun treeAnimation(treeLocation: MutableState<Dp>,  treeComposable: @Composable ()-> Unit) {
    val animatedTreeLocation = animateDpAsState(targetValue = treeLocation.value, label = "animateTreeLocation")
    Box(modifier = Modifier.offset(x = animatedTreeLocation.value)) {treeComposable()}
}
@Composable
fun Tree() {
    animatedBrush(32.sp)
    Icon(
        tint = Color.Unspecified,
        modifier = Modifier
            .height(600.dp)
            .width(400.dp),
        painter = painterResource(id = R.drawable.vector__1_),
        contentDescription = "photo Icon"
    )
}

@Composable
fun Arrow() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val brush = animatedBrush(32.sp)
        Icon(
            tint = Color.White,
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithCache {

                    onDrawWithContent {
                        drawContent()
                        drawRect(brush, blendMode = BlendMode.SrcAtop)
                    }
                },
            painter = painterResource(id = R.drawable.union),
            contentDescription = "photo Icon"
        )
    }
}