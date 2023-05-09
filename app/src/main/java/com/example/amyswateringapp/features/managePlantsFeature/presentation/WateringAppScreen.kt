package com.example.amyswateringapp.wateringAppScreen


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
import com.example.amyswateringapp.common.presentation.theme.padding
import com.example.amyswateringapp.features.managePlantsFeature.presentation.graphics
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.onEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun WateringAppHome(
    viewModel: wateringViewModel = hiltViewModel()
) {
    val newPlantState = viewModel.newPlant.collectAsStateWithLifecycle()
    val plantFlow = viewModel.plantsFlow.collectAsStateWithLifecycle()
    WateringAppHomeScaffold(
        newPlantState,
        plantFlow,
    ) { event -> (viewModel::onEvent)(event) }
}

@Composable
fun WateringAppHomeScaffold(
    newPlantState: State<Plant>,
    plantFlow: State<plantListState>,
    event: (onEvent)->Unit,
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

        Column(Modifier.padding(it).fillMaxSize()) {

            PlantListStates(
                plantFlow = plantFlow,
                waterplant =  { plant -> event(onEvent.waterPlant(plant)) },
                makeItRain = {showGraphic.value = true},
                deletePlant = {plant -> event(onEvent.deletePlant(plant))}
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

