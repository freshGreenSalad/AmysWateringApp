package com.example.amyswateringapp.Repository

import com.example.amyswateringapp.IsWatered
import com.example.amyswateringapp.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {

    fun createPlant(plant: Plant)

    fun allPlants(): Flow<IsWatered>

    suspend fun waterPlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)
}