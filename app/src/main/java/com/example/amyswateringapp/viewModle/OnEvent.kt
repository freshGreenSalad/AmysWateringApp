package com.example.amyswateringapp.viewModle

import android.net.Uri
import com.example.amyswateringapp.Plant

sealed interface onEvent{
    object addPlant: onEvent
    data class updatePlantName(val name: String): onEvent
    data class updatePlantPhoto(val photoUri: Uri): onEvent
    data class updatePlantwateringInterval(val days: Int): onEvent
    data class waterPlant(val plant: Plant): onEvent
    data class deletePlant(val plant: Plant): onEvent
}