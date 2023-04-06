package com.example.amyswateringapp.Room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.amyswateringapp.Plant
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest //
class RoomTests {
    lateinit var database: amysWateringAppDatabase
    lateinit var dao:WateringDao

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            amysWateringAppDatabase::class.java,
        ).allowMainThreadQueries().build()
        dao = database.wateringDao()
    }

    @After
    fun tearDown(){
        database.close()
    }


    @Test
    fun testInsertAll(){
        runTest{
            val plant = Plant(1)
            dao.insertAll(plant)
            val firstPlant = dao.getall().first().get(0)
            assertEquals(plant, firstPlant)
        }
    }

    @Test
    fun testdeletePlant(){
        runTest{
            val plant = Plant(1)
            dao.insertAll(plant)
            dao.deletePlant(plant)
            val plantlist = dao.getall().first()
            assertEquals(plantlist, emptyList<Plant>())
        }
    }

    @Test
    fun testUpdataPlant(){
        runTest{
            val plant = Plant(1)
            val newPlant = Plant(1,"newplant")
            dao.insertAll(plant)
            dao.updatePlant(newPlant)
            val plantFlow = dao.getall().first().get(0)
            assertNotSame(plant, plantFlow)
        }
    }
}
