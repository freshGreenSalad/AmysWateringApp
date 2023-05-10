package com.example.amyswateringapp.repository

import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.features.managePlantsFeature.domain.PlantRepository
import kotlinx.coroutines.flow.Flow

class fakeOfflineWaterRepository: PlantRepository {

    val plantList = mutableListOf<Plant>()

    override fun createPlant(plant: Plant) {
        plantList.add(plant)
    }

    override suspend fun waterPlant(plant: Plant) {
        plantList
    }

    override suspend fun deletePlant(plant: Plant) {
        TODO("Not yet implemented")
    }

    override fun allPlants(): Flow<List<Plant>> {
        TODO("Not yet implemented")
    }
}