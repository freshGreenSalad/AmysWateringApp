package com.example.amyswateringapp.DI

import androidx.room.RoomDatabase
import com.example.amyswateringapp.Room.WateringDao
import com.example.amyswateringapp.Room.amysWateringAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideWateringDao(database: amysWateringAppDatabase): WateringDao = database.wateringDao()

}