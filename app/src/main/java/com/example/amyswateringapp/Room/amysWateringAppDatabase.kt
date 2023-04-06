package com.example.amyswateringapp.Room

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

abstract class amysWateringAppDatabase:RoomDatabase() {
    abstract fun wateringDao(): WateringDao
}