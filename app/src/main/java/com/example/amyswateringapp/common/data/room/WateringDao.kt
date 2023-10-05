package com.example.amyswateringapp.common.data.room

import androidx.room.*
import com.example.amyswateringapp.Plant
import kotlinx.coroutines.flow.Flow

@Dao
interface WateringDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg plant: Plant)

    @Query("SELECT * FROM Plant")
    fun getAll(): Flow<List<Plant>>

    @Update
    fun updatePlant (vararg: Plant)

    @Delete
    fun deletePlant (plant: Plant)
}
