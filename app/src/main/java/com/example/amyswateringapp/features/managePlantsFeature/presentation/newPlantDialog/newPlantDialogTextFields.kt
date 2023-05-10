package com.example.amyswateringapp.features.managePlantsFeature.presentation.newPlantDialog

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.common.presentation.MyKeyboards

@Composable
fun PlantNameTextField(
    plant: State<Plant>,
    updateNewPlantName: (String)->Unit,
    focusManager: FocusManager
) {
    OutlinedTextField(
        placeholder = { Text(text = "What is your Plant Name?") },
        value = plant.value.plantName,
        onValueChange = { updateNewPlantName(it) },
        keyboardOptions = MyKeyboards().StringKeyBoard,
        keyboardActions = KeyboardActions(
            onDone = {focusManager.moveFocus(FocusDirection.Next)}
        )
    )
}

@Composable
fun PlantWateringTimeTextField(
    plant: State<Plant>,
    updateNewPlantWateringTime: (Int) ->Unit,
    focusManager: FocusManager
) {
    OutlinedTextField(
        placeholder = { Text(text = "How Many Days Between Watering?") },
        value = if(plant.value.WaterIntervalTime == 0){""}
        else{plant.value.WaterIntervalTime.toString()},
        onValueChange = {
            if (it==""){updateNewPlantWateringTime(0)} else
            {updateNewPlantWateringTime(it.toInt())}
        },
        keyboardOptions = MyKeyboards().numbersKeyBoard,
        keyboardActions = KeyboardActions(
            onDone = {focusManager.moveFocus(FocusDirection.Next)}
        )
    )
}