package com.example.amyswateringapp.common.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.amyswateringapp.LocalDateTimeConverter
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.UriConverters

@Database(
    entities = [Plant::class],
    version = 1,
)

@TypeConverters(UriConverters::class, LocalDateTimeConverter::class)

abstract class AmysWateringAppDatabase:RoomDatabase() {
    abstract fun wateringDao(): WateringDao
}