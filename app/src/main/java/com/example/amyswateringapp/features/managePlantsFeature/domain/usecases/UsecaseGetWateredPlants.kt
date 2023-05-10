package com.example.amyswateringapp.features.managePlantsFeature.domain.usecases

import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.features.managePlantsFeature.domain.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class UsecaseGetWateredPlants  @Inject constructor(
    private val plantRepository: PlantRepository
) {
    operator fun invoke(): Flow<List<Plant>> {
        val wateredPlants = plantRepository.allPlants().map{ plantList->
            plantList.filter { plant ->
                !plant.NextWateringTime.isBefore(LocalDateTime.now())
            }
        }
        return wateredPlants
    }
}