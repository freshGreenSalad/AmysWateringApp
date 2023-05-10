package com.example.amyswateringapp.wateringAppScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.ScreenState
import com.example.amyswateringapp.features.managePlantsFeature.presentation.plantCardSwipableWithBin

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlantListStates(
    screenState: State<ScreenState>,
    waterPlant: (Plant) -> Unit,
    makeItRain:()->Unit,
    deletePlant:(Plant)->Unit,
    wateredPlants: State<List<Plant>>,
    notWateredPlants: State<List<Plant>>,
) {
    Column {
        AnimatedContent(
            targetState= screenState.value,
            transitionSpec= { slideInHorizontally(tween(500),
                initialOffsetX = {fullSize -> fullSize })with slideOutHorizontally(tween(500))
            }
        ) { transitionState ->
            when (transitionState) {
                ScreenState.Empty -> { EmptyPlantScreen()}
                ScreenState.Loading -> { FullScreenProgress()}
                is ScreenState.Success -> {
                        PlantList(
                            waterPlant = { plant ->
                                waterPlant(plant)
                                makeItRain()
                            },
                            deletePlant = deletePlant,
                            wateredPlants = wateredPlants,
                            notWateredPlants = notWateredPlants
                        )
                }
            }
        }
    }
}

@Composable
fun EmptyPlantScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.alpha(0.75f),
            text = "You don't have any plants yet"
        )
    }
}

@Composable
fun FullScreenProgress() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun PlantList(
    waterPlant: (Plant) -> Unit,
    deletePlant:(Plant)->Unit,
    wateredPlants: State<List<Plant>>,
    notWateredPlants: State<List<Plant>>,
) {
    val wateredPlantsAlready  = wateredPlants.value
    val plantsToBeWatered = notWateredPlants.value
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
    ){
        notWateredPlantsStickyHeader(wateredPlantsAlready,plantsToBeWatered)
        items(plantsToBeWatered){plant ->
            plantCardSwipableWithBin(plant = plant, waterPlant = { waterPlant(plant) }, deletePlant)
        }
        wateredPlantsStickyHeader(wateredPlantsAlready,plantsToBeWatered)
        items(wateredPlantsAlready){ plant ->
            plantCardSwipableWithBin(plant = plant, waterPlant = { waterPlant(plant) }, deletePlant)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.wateredPlantsStickyHeader(wateredPlantsAlready: List<Plant>, plantsToBeWatered: List<Plant>) {
    stickyHeader {
        when {
            plantsToBeWatered.isNotEmpty() && wateredPlantsAlready.isNotEmpty() -> StickyHeaderText(text = "Plants that don't need watering")
            plantsToBeWatered.isNotEmpty() -> {}
            wateredPlantsAlready.isNotEmpty() -> {StickyHeaderText(text = "None of your plants need watering!")}
            else -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.notWateredPlantsStickyHeader(wateredPlantsAlready: List<Plant>, plantsToBeWatered: List<Plant>) {
    stickyHeader {
        when {
            plantsToBeWatered.isNotEmpty() && wateredPlantsAlready.isNotEmpty() -> StickyHeaderText(text = "Plants that need watering")
            plantsToBeWatered.isNotEmpty() -> StickyHeaderText(text = "All your plants need watering!")
            wateredPlantsAlready.isNotEmpty() -> {}
            else->{}
        }
    }
}

@Composable
fun StickyHeaderText(text:String) {
    Box(modifier = Modifier.padding(16.dp)) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}
