package com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amyswateringapp.DI.IoDispatcher
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.features.managePlantsFeature.domain.PlantRepository
import com.example.amyswateringapp.features.managePlantsFeature.domain.usecases.UsecaseGetNotWateredPlants
import com.example.amyswateringapp.features.managePlantsFeature.domain.usecases.UsecaseGetWateredPlants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WateringViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    GetWateredPlants: UsecaseGetWateredPlants,
    GetNotWateredPlants: UsecaseGetNotWateredPlants
): ViewModel() {
    private val scope = viewModelScope

    val newPlant = MutableStateFlow(Plant())

    val wateredPlants = GetWateredPlants().stateIn(
        initialValue = emptyList(),
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )
    val notWateredPlants = GetNotWateredPlants().stateIn(
        initialValue = emptyList(),
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )

    val screenState = combine(wateredPlants, notWateredPlants) { wateredPlants, notwatered ->
            if(notwatered.isEmpty() && wateredPlants.isEmpty()){
                ScreenState.Empty
            } else {
                ScreenState.Success
            }
        }.stateIn(
            initialValue = ScreenState.Loading,
            scope = viewModelScope,
            started = WhileSubscribed(5000)
        )

    fun onEvent(event: OnEvent){
        when(event){
            is OnEvent.AddPlant -> {
                scope.launch(dispatcher) {
                    val dateTime = LocalDateTime.now()
                    val nextwateringTime = dateTime.plusDays(newPlant.value.WaterIntervalTime.toLong())
                    newPlant.update { it.copy(NextWateringTime = nextwateringTime) }
                    plantRepository.createPlant(newPlant.value)
                    newPlant.value = Plant()

                }
            }
            is OnEvent.UpdatePlantName -> {
                newPlant.update { it.copy(plantName = event.name) }
            }
            is OnEvent.UpdatePlantPhoto -> {
                newPlant.update { it.copy(image = event.photoUri) }
            }
            is OnEvent.UpdatePlantWateringInterval -> {
                newPlant.update { it.copy(WaterIntervalTime = event.days) }
            }
            is OnEvent.WaterPlant -> {
                scope.launch {
                    withContext(dispatcher) {
                        plantRepository.waterPlant(event.plant)
                    }
                }
            }
            is OnEvent.DeletePlant -> {
                scope.launch(dispatcher) {
                    plantRepository.deletePlant(event.plant)

                }
            }
        }
    }
}

sealed interface ScreenState {
    object Loading : ScreenState
    object Empty: ScreenState
    object Success: ScreenState
}