package com.example.amyswateringapp.wateringAppScreen

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.ui.theme.AmysWateringAppTheme
import java.time.LocalDateTime


@Composable
fun addPlantDialog(
    ShowAddPlantDialog: () ->Unit,
    plant: State<Plant>,
    updateNewPlantName: (String)->Unit,
    updateNewPlantWateringTime: (Int) ->Unit,
    updatePlantUri: (Uri)->Unit,
    createPlant: ()-> Unit
) {

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val enabled = remember {derivedStateOf { plant.value.plantName.isNotEmpty()&& plant.value.WaterIntervalTime !=0 } }

        AlertDialog(
        onDismissRequest = {
            updateNewPlantName("")
            updateNewPlantWateringTime(0)
            updatePlantUri(Uri.EMPTY)
            ShowAddPlantDialog()
                           },
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
            TextButton(onClick = {
                updateNewPlantName("")
                updateNewPlantWateringTime(0)
                updatePlantUri(Uri.EMPTY)
                ShowAddPlantDialog()
            })
            { Text("Dismiss") }
        },

        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.semantics { testTag = "addPlantDialog" }
            ) {
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {

                    Text(text = "Add A Plant!", style = MaterialTheme.typography.headlineMedium)
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    placeholder = { Text(text = "What is your Plant Name?") },
                    value = plant.value.plantName,
                    onValueChange = { updateNewPlantName(it) },
                    keyboardOptions = MyKeyboards().StringKeyBoard,
                    keyboardActions = KeyboardActions(
                        onDone = {focusManager.moveFocus(FocusDirection.Next)}
                    )
                )
                Spacer(Modifier.height(16.dp))
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
                Spacer(Modifier.height(16.dp))
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.TakePicturePreview()){bitmap ->
                    if (bitmap != null) {
                        val uri:Uri = saveInMediastore(bitmap = bitmap, context = context, plant = plant.value.plantName)
                        updatePlantUri(uri)
                    }
                }

                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {

                    PhotoPickerButton(updatePlantUri = updatePlantUri, enabled.value)
                    permission(
                        permission = android.Manifest.permission.CAMERA,
                        permissionComposable = {exitLauncher ->
                            CameraComposable(launcher)
                            exitLauncher()
                        },
                        clickableComposable = {click -> cameraButton(click, enabled.value)}
                    )
                }
            }
        }
    )
}

@Composable
fun CameraComposable(launcher: ManagedActivityResultLauncher<Void?, Bitmap?>) {
    launcher.launch(null)
}

@Composable
fun PhotoPickerButton(updatePlantUri: (Uri) -> Unit, enabled: Boolean) {
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia() ){ uri->
        if(uri != null){ updatePlantUri(uri)}
    }
    FilledTonalButton(
        modifier = Modifier.focusable(),
        onClick = {
        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    },
        enabled = enabled
    ) {
        Text("Pick a photo")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cameraButton(onClick:()->Unit, enabled: Boolean) {

    if (enabled){
        FilledTonalButton(
            modifier = Modifier.focusable(),
            onClick = {
                onClick()
            },
        ) {
            Text("Take a photo")
        }
    } else {
        PlainTooltipBox(tooltip = { Text("You need to add a name before you can take a photo") }) {
            FilledTonalButton(
                modifier = Modifier.focusable(),
                onClick = {},
                enabled = false
            ) {
                Text("Take a photo")
            }
        }
    }
}

@Preview
@Composable
fun previewAddPlant() {
    val plant = remember {
        mutableStateOf(Plant(1, "", 5, Uri.EMPTY, LocalDateTime.now()))
    }
    AmysWateringAppTheme() {
        //addPlantDialog({}, plant,{},{}, {}, {})
    }
}

@Preview
@Composable
fun previewCameraButton() {
    AmysWateringAppTheme {
        cameraButton({}, true )
    }
}