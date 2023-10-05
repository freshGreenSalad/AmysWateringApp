package com.example.amyswateringapp.introduction.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.amyswateringapp.common.presentation.NavDestinations

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.Introduction(
    navigateToAppMain: () -> Unit
) {
    composable(
        route = NavDestinations.introduction
    ) {
        AnimationManager(navigateToMain = navigateToAppMain)
    }
}