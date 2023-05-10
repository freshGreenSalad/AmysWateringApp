package com.example.amyswateringapp.features.managePlantsFeature.presentation


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.amyswateringapp.*
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.onEvent
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.plantListState
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.wateringViewModel
import com.example.amyswateringapp.wateringAppScreen.AddPlantFab
import com.example.amyswateringapp.wateringAppScreen.PlantListStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@Composable
fun WateringAppHome(
    viewModel: wateringViewModel = hiltViewModel()
) {
    val wateredPlants = viewModel.wateredPlants.collectAsStateWithLifecycle()
    val notWateredPlants = viewModel.notWateredPlants.collectAsStateWithLifecycle()

    val newPlantState = viewModel.newPlant.collectAsStateWithLifecycle()
    val plantFlow = viewModel.plantsFlow.collectAsStateWithLifecycle()

    WateringAppHomeScaffold(
        newPlantState = newPlantState,
        plantFlow = plantFlow,
     event = { event -> (viewModel::onEvent)(event) },
        wateredPlants = wateredPlants,
        notWateredPlants = notWateredPlants
    )
}

@Composable
fun WateringAppHomeScaffold(
    newPlantState: State<Plant>,
    plantFlow: State<plantListState>,
    event: (onEvent)->Unit,
    wateredPlants: State<List<Plant>>,
    notWateredPlants: State<List<Plant>>,
) {

    val showAddPlantDialog = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            AddPlantFab { showAddPlantDialog.value = !showAddPlantDialog.value }
        },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
    ){
        val scope = rememberCoroutineScope()
        val showGraphic = remember { mutableStateOf(false) }

        Column(
            Modifier
                .padding(it)
                .fillMaxSize()) {
            PlantListStates(
                plantFlow = plantFlow,
                waterplant =  { plant -> event(onEvent.waterPlant(plant)) },
                makeItRain = {showGraphic.value = true},
                deletePlant = {plant -> event(onEvent.deletePlant(plant))},
                wateredPlants = wateredPlants,
                notWateredPlants = notWateredPlants,
            )

            if (showAddPlantDialog.value){
                addPlantDialog(
                    ShowAddPlantDialog = {showAddPlantDialog.value = !showAddPlantDialog.value},
                    plant = newPlantState,
                    updateNewPlantName = {plantName -> event(onEvent.updatePlantName(plantName))},
                    updateNewPlantWateringTime = {wateringTime -> event(onEvent.updatePlantwateringInterval(wateringTime))},
                    updatePlantUri = {uri -> event(onEvent.updatePlantPhoto(uri))},
                    createPlant = {event(onEvent.addPlant)}
                )
            }
        }

        AnimatedVisibility(visible = showGraphic.value,
            enter = fadeIn(tween(200)),
            exit = fadeOut(tween(200))
            ){
            graphics()
            LaunchedEffect(key1 = true){
                scope.launch {
                    delay(1000)
                    showGraphic.value = false
                }
            }
        }
    }
}

