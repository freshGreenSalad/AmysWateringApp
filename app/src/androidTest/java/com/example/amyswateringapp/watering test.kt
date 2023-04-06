package com.example.amyswateringapp

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.amyswateringapp.wateringAppScreen.WateringAppHomeScaffold
import com.example.amyswateringapp.wateringAppScreen.addPlantDialog
import org.junit.Rule
import org.junit.Test

class wateringTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testIfRainAppears() {
        val state = mutableStateOf(Plant())
        val isWatered = IsWatered(
            needsWatering = null,
            doesNotNeedWatering = listOf(Plant()))
        val s = mutableStateOf(plantListState.Success(plantFlow = isWatered))
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent { WateringAppHomeScaffold(state,s,{})}
        composeRule.onNodeWithTag("cloudClick" ).performClick()
        composeRule.mainClock.advanceTimeBy(500)
        composeRule.onNodeWithTag("cloud").assertExists()
    }

    @Test
    fun testIfSwipePlantCardShowsRubishbin() {
        val state = mutableStateOf(Plant())
        val isWatered = IsWatered(
            needsWatering = null,
            doesNotNeedWatering = listOf(Plant()))
        val s = mutableStateOf(plantListState.Success(plantFlow = isWatered))
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent { WateringAppHomeScaffold(state,s,{})}
        composeRule.onNodeWithTag("plantCard" ).performTouchInput { swipeRight(0f, 100f) }
        composeRule.mainClock.advanceTimeBy(500)
        composeRule.onNodeWithTag("bin").assertExists()
    }

    @Test
    fun testIfClickingFabShowsDialog() {
        val state = mutableStateOf(Plant())
        val s = mutableStateOf(plantListState.Empty)
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent { WateringAppHomeScaffold(state,s,{})}
        composeRule.onNode( hasClickAction()).performClick()
        composeRule.mainClock.advanceTimeBy(1000)
        composeRule.onAllNodes(isRoot()).assertCountEquals(2)
    }

    @Test
    fun testIfClickingFabThenDismissFabNotShowing() {
        val state = mutableStateOf(Plant())
        val s = mutableStateOf(plantListState.Empty)
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent { WateringAppHomeScaffold(state,s,{})}
        composeRule.onNode( hasClickAction()).performClick()
        composeRule.mainClock.advanceTimeBy(1000)
        composeRule.onNode(hasText("Dismiss")).performClick()
        composeRule.mainClock.advanceTimeBy(1000)
        composeRule.onAllNodes(isRoot()).assertCountEquals(1)
    }

    @Test
    fun testIfButtonEnabledInDialogWhenPlantNameAndIntergerFull() {
        val uri = Uri.EMPTY
        val plant = mutableStateOf(Plant(1,"asdf",2,uri ))
        composeRule.setContent {
            addPlantDialog(
                ShowAddPlantDialog = { /*TODO*/ },
                plant = plant,
                updateNewPlantName = { } ,
                updateNewPlantWateringTime = {},
                updatePlantUri = {},
                createPlant = {}
            )
        }
        composeRule.onNode(hasClickAction() and hasText("Pick a photo") ).assertIsEnabled()
    }

    @Test
    fun testIfButtonDisabledInDialogWhenPlantNameAndIntergerEmpty() {
        val uri = Uri.EMPTY
        val plant = mutableStateOf(Plant(0,"",2,uri ))
        composeRule.setContent {
            addPlantDialog(
                ShowAddPlantDialog = { /*TODO*/ },
                plant = plant,
                updateNewPlantName = { } ,
                updateNewPlantWateringTime = {},
                updatePlantUri = {},
                createPlant = {}
            )
        }
        composeRule.onNode(hasClickAction() and hasText("Pick a photo") ).assertIsNotEnabled()
    }

}