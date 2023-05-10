package com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle

import android.net.Uri
import com.example.amyswateringapp.Plant

sealed interface OnEvent{
    object AddPlant: OnEvent
    data class UpdatePlantName(val name: String): OnEvent
    data class UpdatePlantPhoto(val photoUri: Uri): OnEvent
    data class UpdatePlantWateringInterval(val days: Int): OnEvent
    data class WaterPlant(val plant: Plant): OnEvent
    data class DeletePlant(val plant: Plant): OnEvent
}