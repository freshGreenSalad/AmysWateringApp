package com.example.amyswateringapp.features.introduction.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.amyswateringapp.common.presentation.NavDestinations

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.introduction(
    navigateToAppMain: () -> Unit
) {
    composable(
        route = NavDestinations.introduction
    ) {
        AnimationManager(navigateToMain = navigateToAppMain)
    }
}