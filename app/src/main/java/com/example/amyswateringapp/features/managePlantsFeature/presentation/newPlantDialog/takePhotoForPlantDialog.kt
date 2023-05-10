package com.example.amyswateringapp.features.managePlantsFeature.presentation.newPlantDialog

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.common.presentation.permission
import com.example.amyswateringapp.features.managePlantsFeature.data.MediaStoreFunctions

@Composable
fun SetPlantPhotoButtons(
    updatePlantUri: (Uri) -> Unit,
    enabled: State<Boolean>,
    plant: State<Plant>
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()){ bitmap ->
        if (bitmap != null) {
            val uri: Uri = MediaStoreFunctions().saveInMediastore(bitmap = bitmap, context = context, plant = plant.value.plantName)
            updatePlantUri(uri)
        }
    }

    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {

        PhotoPickerButton(updatePlantUri = updatePlantUri, enabled.value)
        permission(
            permission = Manifest.permission.CAMERA,
            permissionComposable = {exitLauncher ->
                CameraComposable(launcher)
                exitLauncher()
            },
            clickableComposable = {click -> cameraButton(click, enabled.value) }
        )
    }
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
            onClick = { onClick()},
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