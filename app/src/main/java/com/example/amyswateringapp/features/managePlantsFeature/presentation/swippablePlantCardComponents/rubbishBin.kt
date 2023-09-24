package com.example.amyswateringapp.features.managePlantsFeature.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.amyswateringapp.R


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RubbishBinIconAnimationRed(swipeableState: SwipeableState<Int>, modifier: Modifier = Modifier) {

    val screenWidth = LocalConfiguration.current.screenHeightDp.dp

    AnimateBinVisibility(swipeableState = swipeableState) {
        Box(
            modifier
                .size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(80.dp),
                painter = painterResource(R.drawable.delete),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RubbishBinIconAnimationTransperant(swipeableState: SwipeableState<Int>, updateStateOffset:(Dp)-> Unit, modifier: Modifier = Modifier) {
    val screenWidth = LocalConfiguration.current.screenHeightDp.dp

    AnimateBinVisibility(swipeableState = swipeableState) {
        Box(
            modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {

            Box(
                Modifier
                    .size(80.dp)
                    .zIndex(1f)
            ) {
                Icon(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color.Black)
                        ) { updateStateOffset(screenWidth) },
                    painter = painterResource(R.drawable.delete),
                    contentDescription = "",
                    tint = Color.Transparent
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimateBinVisibility(swipeableState: SwipeableState<Int>, content: @Composable ()->Unit) {
    AnimatedVisibility(
        modifier = Modifier.zIndex(1f),
        visible = swipeableState.currentValue == 1,
        enter = fadeIn(tween(100)),
        exit = fadeOut(tween(100))
    ) {
        content()
    }

}