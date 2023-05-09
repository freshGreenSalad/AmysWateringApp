package com.example.amyswateringapp.wateringAppScreen

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material.swipeable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.amyswateringapp.IsWatered
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.R
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.plantListState
import com.example.amyswateringapp.common.presentation.theme.AmysWateringAppTheme
import java.time.LocalDateTime


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlantListStates(
    plantFlow: State<plantListState>,
    waterplant: (Plant) -> Unit, makeItRain:()->Unit,
    deletePlant:(Plant)->Unit
) {
    Column {


        val targetState :State<String> = remember{
            derivedStateOf(){
                when (plantFlow.value){
                    is plantListState.Success -> "success"
                    is plantListState.Empty -> "empty"
                    is plantListState.Loading -> "loading"
                    else -> {""}
                }
            }
        }

        AnimatedContent(
            targetState= targetState.value,
            transitionSpec= { slideInHorizontally(tween(500),
                initialOffsetX = {fullSize -> fullSize })with slideOutHorizontally(tween(500))
            }
        ) { transitionState ->
            when (transitionState) {
                 "empty" ->  {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.alpha(0.75f),
                            text = "You don't have any plants yet"
                        )
                    }
                }
                 "loading" -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                  "success"   -> {
                      if(plantFlow.value is plantListState.Success) {
                              PlantList(
                                  (plantFlow.value as plantListState.Success).plantFlow,
                                  waterplant,
                                  makeItRain = makeItRain,
                                  deletePlant
                              )
                          }
                }
                else -> {
                    Text("else")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlantList(
    plantFlow: IsWatered,
    waterplant: (Plant) -> Unit,
    makeItRain:()->Unit,
    deletePlant:(Plant)->Unit
) {
    val (WateredPlants, plantsToBeWatered) = plantFlow
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
    ){
        stickyHeader {
            when {
                !plantsToBeWatered.isNullOrEmpty() && !WateredPlants.isNullOrEmpty() -> stickyHeaderText(text = "Plants that need watering")
                !plantsToBeWatered.isNullOrEmpty() && WateredPlants.isNullOrEmpty()-> {stickyHeaderText(text = "All your plants need watering!")}
                plantsToBeWatered.isNullOrEmpty() && !WateredPlants.isNullOrEmpty()-> {}
                else->{}
            }
        }
        items(plantsToBeWatered.orEmpty()){plant ->
            plantCard(plant = plant, waterplant = waterplant, makeItRain = makeItRain, deletePlant)
        }
        stickyHeader {
            when {
                !plantsToBeWatered.isNullOrEmpty() && !WateredPlants.isNullOrEmpty() -> stickyHeaderText(text = "Plants that don't need watering")
                !plantsToBeWatered.isNullOrEmpty() && WateredPlants.isNullOrEmpty()-> {}
                plantsToBeWatered.isNullOrEmpty() && !WateredPlants.isNullOrEmpty()-> {stickyHeaderText(text = "None of your plants need watering!")}
                else -> {}
            }
        }
        items(WateredPlants.orEmpty()){plant ->
            plantCard(plant = plant, waterplant = waterplant, makeItRain, deletePlant)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun plantCard(
    plant:Plant,
    waterplant:(Plant)->Unit,
    makeItRain:()->Unit,
    deletePlant:(Plant)->Unit
) {
    val swipeableState = rememberSwipeableState(0)
    val deleteStateOffset = remember { mutableStateOf(0.dp) }
    Box{

    swipable(
        deletePlant = { deletePlant(plant) },
        swipeableState = swipeableState,
        deleteStateOffset = deleteStateOffset,
        content = {
            Box {
                Row {
                    plantImage(plant.image)
                    Column(Modifier.padding(8.dp)) {
                        Text(text = plant.plantName)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(modifier = Modifier.width(150.dp), text = whenToNextWater(plant))
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .semantics { testTag = "cloudClick" }
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(color = MaterialTheme.colorScheme.onSurface),
                    ) {
                        makeItRain()
                        waterplant(plant)
                    },
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material.Icon(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(id = R.drawable.cloudy_snowing),
                    contentDescription = "Water Icon"
                )
            }
        }
    )
    animatevis(swipeableState = swipeableState) {
        Box(
            Modifier
                .size(100.dp)
                .semantics { testTag = "bin" },
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
    animatevis(swipeableState = swipeableState) {
        Box(
            Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            val screenWidth = LocalConfiguration.current.screenHeightDp.dp
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
                        ) { deleteStateOffset.value = screenWidth },
                    painter = painterResource(R.drawable.delete),
                    contentDescription = "",
                    tint = Color.Transparent
                )
            }
        }
    }
}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun animatevis(swipeableState: SwipeableState<Int>, content: @Composable ()->Unit) {
    AnimatedVisibility(
        modifier = Modifier.zIndex(1f),
        visible = swipeableState.currentValue == 1,
        enter = fadeIn(tween(100)),
        exit = fadeOut(tween(100))
    ) {
        content()
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun swipable(
    content: @Composable() ()-> Unit,
    deletePlant: () -> Unit,
    deleteStateOffset: MutableState<Dp>,
    swipeableState: SwipeableState<Int>
) {
    val zIndexOfRubbishIcon = remember { mutableStateOf(0f) }


    val animateDeleteStateOffset = animateDpAsState(
        targetValue = deleteStateOffset.value,
        finishedListener = { deletePlant() }
    )


    val dpToPx = with(LocalDensity.current) { 100.dp.toPx() }

    val pxToDp = with(LocalDensity.current) { swipeableState.offset.value.toDp() }

    val anchors = mapOf(0f to 0, dpToPx to 1,)

    LaunchedEffect(key1 = swipeableState.targetValue) {
        if (swipeableState.currentValue == 1) {
            zIndexOfRubbishIcon.value = 1f
        } else {
            zIndexOfRubbishIcon.value = 0f
        }
    }
        Row(
            modifier = Modifier
                .zIndex(0.5f)
                .swipeable(
                    state = swipeableState, // remembers the current anchor
                    anchors = anchors, //points where the swipeable child can stop
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },// how much to move to next anchor
                    orientation = Orientation.Horizontal // direction of swipe
                )
                .semantics { testTag = "plantCard" }
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

@Composable
fun whenToNextWater(plant:Plant): String{
    val datetime = remember{LocalDateTime.now()}
    // whenToNextWaterText shows a day of the week if less then 7 days away and an int if more then 7
    val whenToNextWaterText  = if (plant.NextWateringTime.minusDays(7) <= datetime){
        "Water on " + plant.NextWateringTime.dayOfWeek.toString().lowercase()
    } else { "Water in "+(plant.NextWateringTime.dayOfYear - datetime.dayOfYear).toString()}
    return whenToNextWaterText
}

@Composable
fun plantImage(uri: Uri) {
    if (uri == Uri.EMPTY) {
        androidx.compose.material.Icon(
            modifier = Modifier
                .size(100.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            painter = painterResource(id = R.drawable.picture),
            contentDescription = "photo Icon"
        )
    } else {
        val painter = rememberAsyncImagePainter(model = uri)
        Image(
            modifier = Modifier
                .size(100.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = "Image of Plant"
        )
    }
}


@Preview
@Composable
fun previewplantCard() {
    val plant = Plant(1, "zizi", 5, Uri.EMPTY, LocalDateTime.now())
    AmysWateringAppTheme() {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)) {
            plantCard(plant, {},{},{})
        }
    }
}

@Composable
fun stickyHeaderText(text:String) {
    Box(modifier = Modifier.padding(16.dp)) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Preview
@Composable
fun previewStickyHeaderText() {
    AmysWateringAppTheme {
        Box (Modifier.background(MaterialTheme.colorScheme.surfaceVariant)){
            stickyHeaderText(text = "This is a test for amys app sticky header")
        }
    }
}