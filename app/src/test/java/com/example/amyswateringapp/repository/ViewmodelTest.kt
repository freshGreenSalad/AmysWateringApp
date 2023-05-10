package com.example.amyswateringapp.repository


import com.example.amyswateringapp.features.managePlantsFeature.domain.PlantRepository
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.WateringViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ViewmodelTest {
    lateinit var viewModel: WateringViewModel
    lateinit var repository: PlantRepository
    val dispatcher: TestDispatcher = StandardTestDispatcher()


   /* @Before
    fun setUp() {
        repository = fakeOfflineWaterRepository()
        viewModel = wateringViewModel(repository, dispatcher)
        Dispatchers.setMain(dispatcher = dispatcher)
    }*/


    @Test
    fun `test that create plant adds a plant to the plant repository`( ) {
        runTest {

        }
    }


    @After
    fun teardown(){
        Dispatchers.resetMain()
    }
}