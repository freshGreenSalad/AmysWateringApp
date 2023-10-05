package com.example.amyswateringapp.introduction.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.amyswateringapp.R
import com.example.amyswateringapp.common.presentation.animatedBrush
import com.example.amyswateringapp.introduction.domain.Position
import com.example.amyswateringapp.introduction.domain.end
import com.example.amyswateringapp.introduction.domain.middle
import com.example.amyswateringapp.introduction.domain.none
import com.example.amyswateringapp.introduction.domain.start

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimationManager(
    dragState: AnchoredDraggableState<Position> = dragState(),
    navigateToMain: () -> Unit
){
    //state holders
    val showSwipeRightArrowAndText = remember {mutableStateOf(true) }

    val animationList = listOf(
        remember{ mutableStateOf(start) },
        remember{ mutableStateOf(none) },
        remember{ mutableStateOf(none) }
    )

    val treeLocation = remember { mutableStateOf(400.dp) }
    //state holders end

    when (dragState.targetValue){
        Position.One -> {
            animationList[0].value = start
            showSwipeRightArrowAndText.value = true
        }
        Position.Two ->{
            showSwipeRightArrowAndText.value = false
            animationList[0].value = middle
            animationList[1].value=  start
            treeLocation.value = 300.dp
        }

        Position.Three -> {
            animationList[0].value = end
            animationList[1].value = middle
            animationList[2].value = start
            treeLocation.value = 200.dp
        }
        Position.Four -> {
            animationList[1].value = end
            animationList[2].value = middle
            treeLocation.value = 100.dp

        }
        Position.Five -> {
            animationList[2].value = end
            treeLocation.value = 0.dp
        }
    }

    Box(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.primary)
        .fillMaxSize()
        .anchoredDraggable(dragState, Orientation.Horizontal),
        contentAlignment = Alignment.Center
    ) {

        treeAnimation( treeLocation) { Tree() }

        animationList.forEach {animation ->
            LeftRightDragAnimation(
                color = Color.Red,
                goIn = animation,
                xOffset = 1250.0f,
                reverse = 1f,
                text = stringResource(R.string.IntroductionOne)
            ) { color, animateYOffset, animateXOffset, xOffset, goIn, text ->
                AnimateableCircle(
                    color, animateYOffset, animateXOffset, xOffset, goIn, text
                )
            }
        }
        if(showSwipeRightArrowAndText.value) { ShimmerSwipeRightToStart()}
    }
}

@Composable
fun ShimmerSwipeRightToStart(){
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun dragState():AnchoredDraggableState<Position> {
    val density = LocalDensity.current
    return  remember {
        AnchoredDraggableState(
            initialValue = Position.One,
            anchors = DraggableAnchors {
                Position.One at 0.0f
                Position.Two at -1250.0f
                Position.Three at -2500.0f
                Position.Four at -3750.0f
                Position.Five at -5000.0f
            },
            positionalThreshold = {totalDistance:Float -> totalDistance *0.5f },
            velocityThreshold = {with(density) {100.dp.toPx()} },
            animationSpec = tween(),
        )
    }
}