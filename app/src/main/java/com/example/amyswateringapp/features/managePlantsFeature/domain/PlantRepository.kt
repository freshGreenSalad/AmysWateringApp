package com.example.amyswateringapp.features.managePlantsFeature.domain

import com.example.amyswateringapp.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {

    fun createPlant(plant: Plant)

    fun allPlants(): Flow<List<Plant>>

    suspend fun waterPlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)
}