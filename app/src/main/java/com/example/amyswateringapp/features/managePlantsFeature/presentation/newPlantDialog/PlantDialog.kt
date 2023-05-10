package com.example.amyswateringapp.features.managePlantsFeature.presentation.newPlantDialog

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.R

@Composable
fun AddPlantDialog(
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
            ) { Text(stringResource(R.string.confirm)) }
        },
        dismissButton = {
            TextButton(onClick = {onDismiss()})
            { Text(stringResource(R.string.dismiss)) }
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
        Text(text = stringResource(R.string.addAPlant), style = MaterialTheme.typography.headlineMedium)
    }
}