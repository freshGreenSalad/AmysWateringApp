package com.example.amyswateringapp.repository

import app.cash.turbine.testIn
import com.example.amyswateringapp.IsWatered
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.features.managePlantsFeature.domain.PlantRepository
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.onEvent
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.wateringViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ViewmodelTest {
    lateinit var viewModel: wateringViewModel
    lateinit var repository: PlantRepository
    val dispatcher: TestDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        repository = fakeOfflineWaterRepository()
        viewModel = wateringViewModel(repository, dispatcher)
        Dispatchers.setMain(dispatcher = dispatcher)
    }


    @Test
    fun `test that create plant adds a plant to the plant repository`( ) {
        runTest {
            val beforeadd = repository.allPlantsToBeChanged().testIn(backgroundScope)
            assertEquals(IsWatered(listOf<Plant>(), listOf<Plant>()), beforeadd.awaitItem())
            viewModel.onEvent(onEvent.addPlant)
            advanceUntilIdle()
            val afteradd = repository.allPlantsToBeChanged().testIn(backgroundScope)
            assertNotEquals(IsWatered(listOf<Plant>(), listOf<Plant>()), afteradd.awaitItem())
        }
    }


    @After
    fun teardown(){
        Dispatchers.resetMain()
    }
}