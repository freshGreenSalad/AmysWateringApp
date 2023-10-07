package com.example.amyswateringapp.features.introduction.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amyswateringapp.R
import com.example.amyswateringapp.common.presentation.animatedBrush


@Composable
fun ArrowShimmerAndText(){
    Text(
        modifier = Modifier.width(400.dp),
        text = "swipe Right to get started",
        style = MaterialTheme.typography.headlineLarge.copy(
            brush = animatedBrush(32.sp)
        ),
        textAlign = TextAlign.Center,
    )
    Arrow()
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
                .size(100.dp)
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