package com.example.amyswateringapp.DI

import com.example.amyswateringapp.common.data.room.WateringDao
import com.example.amyswateringapp.common.data.room.AmysWateringAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideWateringDao(database: AmysWateringAppDatabase): WateringDao = database.wateringDao()

}