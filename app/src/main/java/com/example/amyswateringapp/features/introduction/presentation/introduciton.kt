package com.example.amyswateringapp.features.introduction.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import com.example.amyswateringapp.R
import com.example.amyswateringapp.features.introduction.domain.Position
import com.example.amyswateringapp.features.introduction.domain.end
import com.example.amyswateringapp.features.introduction.domain.middle
import com.example.amyswateringapp.features.introduction.domain.none
import com.example.amyswateringapp.features.introduction.domain.start
import kotlinx.coroutines.delay

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

    val text = listOf(
        stringResource(R.string.IntroductionOne),
        stringResource(R.string.IntroductionTwo),
        stringResource(R.string.introductionThree)
    )

    val treeLocation = remember { mutableStateOf(400.dp) }
    val navigation = remember { mutableStateOf(false) }
    //state holders end

    LaunchedEffect(key1 = navigation.value){
        if (navigation.value){
            delay(1000L)
            navigateToMain()
        }
    }

    when (dragState.targetValue){
        Position.One -> {
            animationList[0].value = start
            animationList[1].value=  none
            animationList[2].value = none

            showSwipeRightArrowAndText.value = true
        }
        Position.Two ->{
            showSwipeRightArrowAndText.value = false

            animationList[0].value = middle
            animationList[1].value=  start
            animationList[2].value = none

            treeLocation.value = 50.dp
        }

        Position.Three -> {
            animationList[0].value = end
            animationList[1].value = middle
            animationList[2].value = start

            treeLocation.value = 40.dp
        }
        Position.Four -> {
            animationList[1].value = end
            animationList[2].value = middle

            treeLocation.value = 30.dp

        }
        Position.Five -> {
            animationList[2].value = end
            treeLocation.value = 20.dp
            navigation.value = true
        }
    }

    val localWidth = with(LocalDensity.current){ LocalConfiguration.current.screenWidthDp.dp.toPx() }

    Box(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.primary)
        .fillMaxSize()
        .anchoredDraggable(dragState, Orientation.Horizontal),
        contentAlignment = Alignment.Center
    ) {
    /*val listOfTreeAngles = listOf(10)
        listOfTreeAngles.forEach {angle ->
            treeAnimation(treeLocation, angle) { Tree() }
        }*/
        animationList.forEachIndexed {index, animation  ->
            LeftRightDragAnimation(
                color = Color.Red,
                goIn = animation,
                xOffset = localWidth,
                reverse = if(index % 2 == 0){-1f}else{1f},
                text = text[index]
            ) { color, animateYOffset, animateXOffset, xOffset, goIn, text ->
                AnimateableCircle(
                    color, animateYOffset, animateXOffset, xOffset, goIn, text
                )
            }
        }
        if(showSwipeRightArrowAndText.value) { ArrowShimmerAndText()}
    }
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