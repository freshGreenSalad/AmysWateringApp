package com.example.amyswateringapp.repository

import com.example.amyswateringapp.IsWatered
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.Repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*


class fakeOfflineWaterRepository:PlantRepository {

    val plantList = mutableListOf<Plant>()

    override fun createPlant(plant: Plant) {
        plantList.add(plant)
    }

    override fun allPlants(): Flow<IsWatered> {
        return flowOf(IsWatered(
            needsWatering = Collections.unmodifiableList(plantList),
            doesNotNeedWatering = listOf<Plant>())
        )
    }

    override suspend fun waterPlant(plant: Plant) {
        plantList
    }

    override suspend fun deletePlant(plant: Plant) {
        TODO("Not yet implemented")
    }
}