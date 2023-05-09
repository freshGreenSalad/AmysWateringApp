package com.example.amyswateringapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amyswateringapp.DI.IoDispatcher
import com.example.amyswateringapp.features.managePlantsFeature.domain.PlantRepository
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.onEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class wateringViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): ViewModel() {
    val scope = viewModelScope

    val newPlant = MutableStateFlow(Plant())

    val plantsFlow = plantRepository.allPlants()
        .map {
            if(it.needsWatering.isNullOrEmpty() && it.doesNotNeedWatering.isNullOrEmpty())
            { plantListState.Empty }
            else{
                plantListState.Success(it)
            }
        }
        .stateIn(
            initialValue = plantListState.Loading,
            scope = viewModelScope, //TODO("change to dispatcher")
            started = WhileSubscribed(5000)
        )

    fun onEvent(event: onEvent){
        when(event){
            is onEvent.addPlant -> {
                scope.launch(dispatcher) {
                        val dateTime = LocalDateTime.now()
                        val nextwateringTime = dateTime.plusDays(newPlant.value.WaterIntervalTime.toLong())
                        newPlant.update { it.copy(NextWateringTime = nextwateringTime) }
                        plantRepository.createPlant(newPlant.value)
                        newPlant.value = Plant()

                }
            }
            is onEvent.updatePlantName -> {
                newPlant.update { it.copy(plantName = event.name) }
            }
            is onEvent.updatePlantPhoto -> {
                newPlant.update { it.copy(image = event.photoUri) }
            }
            is onEvent.updatePlantwateringInterval -> {
                newPlant.update { it.copy(WaterIntervalTime = event.days) }
            }
            is onEvent.waterPlant -> {
                scope.launch {
                    withContext(dispatcher) {
                        plantRepository.waterPlant(event.plant)
                    }
                }
            }
            is onEvent.deletePlant -> {
                scope.launch(dispatcher) {
                        plantRepository.deletePlant(event.plant)

                }
            }
        }
    }
}

sealed interface plantListState {
    object Loading : plantListState
    object Empty: plantListState
    data class Success(val plantFlow: IsWatered): plantListState
}