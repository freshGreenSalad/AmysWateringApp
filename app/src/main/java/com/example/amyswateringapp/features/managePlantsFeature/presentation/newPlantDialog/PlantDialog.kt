package com.example.amyswateringapp.features.managePlantsFeature.presentation.newPlantDialog

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.example.amyswateringapp.Plant

@Composable
fun addPlantDialog(
    ShowAddPlantDialog: () ->Unit,
    plant: State<Plant>,
    updateNewPlantName: (String)->Unit,
    updateNewPlantWateringTime: (Int) ->Unit,
    updatePlantUri: (Uri)->Unit,
    createPlant: ()-> Unit
) {
    val enabled = remember {derivedStateOf { plant.value.plantName.isNotEmpty()&& plant.value.WaterIntervalTime !=0 } }

    fun onDismiss(){
        updateNewPlantName("")
        updateNewPlantWateringTime(0)
        updatePlantUri(Uri.EMPTY)
        ShowAddPlantDialog()
    }

    AlertDialog(
        onDismissRequest = {onDismiss()},
        modifier = Modifier,
        confirmButton = {
            TextButton(onClick = {
                createPlant()
                ShowAddPlantDialog()
            },
                enabled = enabled.value
            ) { Text("Confirm") }
        },
        dismissButton = {
            TextButton(onClick = {onDismiss()})
            { Text("Dismiss") }
        },
        text = {
            ContentsOfDialog(plant, updateNewPlantName,updateNewPlantWateringTime,updatePlantUri, enabled)
        }
    )
}

@Composable
fun ContentsOfDialog(
    plant: State<Plant>,
    updateNewPlantName: (String)->Unit,
    updateNewPlantWateringTime: (Int) ->Unit,
    updatePlantUri: (Uri)->Unit,
    enabled: State<Boolean>
) {
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.semantics { testTag = "addPlantDialog" }
    ) {
        DialogHeading()
        Spacer(Modifier.height(16.dp))
        PlantNameTextField(plant, updateNewPlantName, focusManager)
        Spacer(Modifier.height(16.dp))
        PlantWateringTimeTextField(plant, updateNewPlantWateringTime, focusManager)
        Spacer(Modifier.height(16.dp))
        SetPlantPhotoButtons(updatePlantUri, enabled, plant)
    }
}

@Composable
fun DialogHeading() {
    Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Add A Plant!", style = MaterialTheme.typography.headlineMedium)
    }
}