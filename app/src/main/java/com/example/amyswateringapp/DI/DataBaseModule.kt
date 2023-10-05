package com.example.amyswateringapp.DI

import android.content.Context
import androidx.room.Room
import com.example.amyswateringapp.common.data.room.AmysWateringAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AmysWateringAppDatabase =
        Room.databaseBuilder(
            context,
            AmysWateringAppDatabase::class.java,
        "amysWateringAppDatabase"
            ).build()
}