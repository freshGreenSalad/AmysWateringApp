package com.example.amyswateringapp.DI

import com.example.amyswateringapp.OfflinePlantRepository
import com.example.amyswateringapp.features.managePlantsFeature.domain.PlantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsPlantRepository(
        plantRepository: OfflinePlantRepository
    ): PlantRepository
}