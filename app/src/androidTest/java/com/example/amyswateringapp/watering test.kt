package com.example.amyswateringapp

class wateringTest {

   /* @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testIfRainAppears() {
        val state = mutableStateOf(Plant())
        val isWatered = IsWatered(
            needsWatering = null,
            doesNotNeedWatering = listOf(Plant()))
        val s = mutableStateOf(ScreenState.Success(plantFlow = isWatered))
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent { WateringAppHomeScaffold(state,s,{}) }
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
        val s = mutableStateOf(ScreenState.Success(plantFlow = isWatered))
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent { WateringAppHomeScaffold(state,s,{}) }
        composeRule.onNodeWithTag("plantCard" ).performTouchInput { swipeRight(0f, 100f) }
        composeRule.mainClock.advanceTimeBy(500)
        composeRule.onNodeWithTag("bin").assertExists()
    }

    @Test
    fun testIfClickingFabShowsDialog() {
        val state = mutableStateOf(Plant())
        val s = mutableStateOf(ScreenState.Empty)
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent { WateringAppHomeScaffold(state,s,{}) }
        composeRule.onNode( hasClickAction()).performClick()
        composeRule.mainClock.advanceTimeBy(1000)
        composeRule.onAllNodes(isRoot()).assertCountEquals(2)
    }

    @Test
    fun testIfClickingFabThenDismissFabNotShowing() {
        val state = mutableStateOf(Plant())
        val s = mutableStateOf(ScreenState.Empty)
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent { WateringAppHomeScaffold(state,s,{}) }
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
                ShowAddPlantDialog = { *//*TODO*//* },
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
                ShowAddPlantDialog = { *//*TODO*//* },
                plant = plant,
                updateNewPlantName = { } ,
                updateNewPlantWateringTime = {},
                updatePlantUri = {},
                createPlant = {}
            )
        }
        composeRule.onNode(hasClickAction() and hasText("Pick a photo") ).assertIsNotEnabled()
    }*/

}