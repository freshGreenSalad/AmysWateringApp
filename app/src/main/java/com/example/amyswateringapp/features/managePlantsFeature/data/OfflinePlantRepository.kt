package com.example.amyswateringapp

import com.example.amyswateringapp.features.managePlantsFeature.domain.PlantRepository
import com.example.amyswateringapp.common.data.Room.WateringDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class OfflinePlantRepository @Inject constructor(
    val wateringDao: WateringDao
): PlantRepository {

    override fun createPlant(plant: Plant) {
        wateringDao.insertAll(plant)
    }



    override fun allPlants(): Flow<IsWatered> = wateringDao.getall() .map { PlantList ->
        returnIsWatered(PlantList)
    }

    override suspend fun waterPlant(plant: Plant) {
        val newplant = plant.copy(NextWateringTime = LocalDateTime.now().plusDays(plant.WaterIntervalTime.toLong()))
        wateringDao.updatePlant(newplant)

    }

    override suspend fun deletePlant(plant: Plant) {
        wateringDao.deletePlant(plant)
    }
}

data class IsWatered(
    val needsWatering: List<Plant>? = listOf<Plant>(),
    val doesNotNeedWatering: List<Plant>? = listOf<Plant>()
)

fun returnIsWatered(list:List<Plant>):IsWatered{
    val needsWatering: MutableList<Plant> = mutableListOf()
    val doesNotNeedWatering: MutableList<Plant> = mutableListOf()

    val curentTime = LocalDateTime.now()

    list.map { plant ->
            if (plant.NextWateringTime.isBefore(curentTime)) {
                doesNotNeedWatering.add(plant)
            } else {
                needsWatering.add(plant)
            }
        }

    return IsWatered(
        needsWatering = needsWatering,
        doesNotNeedWatering = doesNotNeedWatering
    )

}