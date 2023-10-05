package com.example.amyswateringapp.features.managePlantsFeature.data

import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.features.managePlantsFeature.domain.PlantRepository
import com.example.amyswateringapp.common.data.room.WateringDao
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class OfflinePlantRepository @Inject constructor(
    private val wateringDao: WateringDao
): PlantRepository {

    override fun createPlant(plant: Plant) {
        wateringDao.insertAll(plant)
    }

    override fun allPlants(): Flow<List<Plant>> = wateringDao.getAll()

    override suspend fun waterPlant(plant: Plant) {
        val newplant = plant.copy(NextWateringTime = LocalDateTime.now().plusDays(plant.WaterIntervalTime.toLong()))
        wateringDao.updatePlant(newplant)

    }

    override suspend fun deletePlant(plant: Plant) {
        wateringDao.deletePlant(plant)
    }
}