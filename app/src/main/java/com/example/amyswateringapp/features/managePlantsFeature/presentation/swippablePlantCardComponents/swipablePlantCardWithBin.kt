package com.example.amyswateringapp.features.managePlantsFeature.presentation.swippablePlantCardComponents

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.features.managePlantsFeature.presentation.RubbishBinIconAnimationRed
import com.example.amyswateringapp.features.managePlantsFeature.presentation.RubbishBinIconAnimationTransperant

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlantCardSwipeableWithBin(
    plant: Plant,
    waterPlant:()->Unit,
    deletePlant:(Plant)->Unit
) {
    val swipeableState = rememberSwipeableState(0)
    Box {// box is required to keep rubbish bin icon on same line
        val deleteStateOffset = remember { mutableStateOf(0.dp) }
        RubbishBinIconAnimationRed(swipeableState, Modifier.zIndex(0f))
        Swipeable(
            deletePlant = { deletePlant(plant) },
            swipeableState = swipeableState,
            deleteStateOffset = deleteStateOffset,
            content = { PlantCard(plant, waterPlant) },
            modifier = Modifier.zIndex(0.5f)
        )
        RubbishBinIconAnimationTransperant(swipeableState, modifier = Modifier.zIndex(1f), updateStateOffset = { deleteStateOffset.value = it })

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Swipeable(
    content: @Composable ()-> Unit,
    deletePlant: () -> Unit,
    deleteStateOffset: MutableState<Dp>,
    swipeableState: SwipeableState<Int>,
    modifier: Modifier = Modifier
) {
    val zIndexOfRubbishIcon = remember { mutableStateOf(0f) }
    val dpToPx = with(LocalDensity.current) { 100.dp.toPx() }
    val pxToDp = with(LocalDensity.current) { swipeableState.offset.value.toDp() }
    val anchors = mapOf(0f to 0, dpToPx to 1)

    val animateDeleteStateOffset = animateDpAsState(
        targetValue = deleteStateOffset.value,
        finishedListener = { deletePlant() }
    )

    LaunchedEffect(key1 = swipeableState.targetValue) {
        if (swipeableState.currentValue == 1) {
            zIndexOfRubbishIcon.value = 1f
        } else {
            zIndexOfRubbishIcon.value = 0f
        }
    }
    Row(
        modifier = modifier
            .zIndex(0.5f)
            .swipeable(
                state = swipeableState, // remembers the current anchor
                anchors = anchors, //points where the swipeable child can stop
                thresholds = { _, _ -> FractionalThreshold(0.3f) },// how much to move to next anchor
                orientation = Orientation.Horizontal // direction of swipe
            )
            .offset(
                pxToDp + animateDeleteStateOffset.value,
                0.dp
            )
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(3.dp, shape = RoundedCornerShape(8.dp))
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        content()
    }

}

