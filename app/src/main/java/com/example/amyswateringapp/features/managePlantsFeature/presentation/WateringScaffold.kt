package com.example.amyswateringapp.features.managePlantsFeature.presentation

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.amyswateringapp.*
import com.example.amyswateringapp.features.managePlantsFeature.presentation.newPlantDialog.AddPlantDialog
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.OnEvent
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.ScreenState
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.WateringViewModel
import com.example.amyswateringapp.wateringAppScreen.AddPlantFab
import com.example.amyswateringapp.wateringAppScreen.PlantListStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun WateringAppHome(
    viewModel: WateringViewModel
) {
    WateringAppHomeScaffold(
        newPlantState = viewModel.newPlant.collectAsStateWithLifecycle(),
        screenState = viewModel.screenState.collectAsStateWithLifecycle(),
        event = { event -> (viewModel::onEvent)(event) },
        wateredPlants = viewModel.wateredPlants.collectAsStateWithLifecycle(),
        notWateredPlants = viewModel.notWateredPlants.collectAsStateWithLifecycle()
    )
}

@Composable
fun WateringAppHomeScaffold(
    newPlantState: State<Plant>,
    screenState: State<ScreenState>,
    event: (OnEvent)->Unit,
    wateredPlants: State<List<Plant>>,
    notWateredPlants: State<List<Plant>>,
) {
    val scope = rememberCoroutineScope()
    val showGraphic = remember { mutableStateOf(false) }
    val showAddPlantDialog = remember { mutableStateOf(false) }



    Scaffold(
        floatingActionButton = {
            AddPlantFab { showAddPlantDialog.value = !showAddPlantDialog.value }
        },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            PlantListStates(
                screenState = screenState,
                waterPlant = { plant -> event(OnEvent.WaterPlant(plant)) },
                makeItRain = { showGraphic.value = true },
                deletePlant = { plant -> event(OnEvent.DeletePlant(plant)) },
                wateredPlants = wateredPlants,
                notWateredPlants = notWateredPlants,
            )
            PrivacyPolicyButton()

            if (showAddPlantDialog.value) {
                AddPlantDialog(
                    ShowAddPlantDialog = { showAddPlantDialog.value = !showAddPlantDialog.value },
                    plant = newPlantState,
                    updateNewPlantName = { plantName -> event(OnEvent.UpdatePlantName(plantName)) },
                    updateNewPlantWateringTime = { wateringTime ->
                        event(
                            OnEvent.UpdatePlantWateringInterval(
                                wateringTime
                            )
                        )
                    },
                    updatePlantUri = { uri -> event(OnEvent.UpdatePlantPhoto(uri)) },
                    createPlant = { event(OnEvent.AddPlant) }
                )
            }
        }

    }


    AnimatedVisibility(
        visible = showGraphic.value,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween(200))
    ) {
        RainGraphic()
        LaunchedEffect(key1 = true){
            scope.launch {
                delay(1000)
                showGraphic.value = false
            }
        }
    }
}

@Composable
fun PrivacyPolicyButton() {
    val context = LocalContext.current
    val url = "https://github.com/freshGreenSalad/AmysWateringApp/blob/master/Privacy%20Policy"
    val  builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()

    Text(modifier = Modifier
        .padding(4.dp)
        .clickable {
            customTabsIntent.launchUrl(context, Uri.parse(url))
        }, text = "privacy Policy", color = Color.Blue)
}


