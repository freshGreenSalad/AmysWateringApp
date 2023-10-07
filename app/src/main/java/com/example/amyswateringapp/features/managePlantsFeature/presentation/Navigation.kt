package com.example.amyswateringapp.features.managePlantsFeature.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.amyswateringapp.common.presentation.NavDestinations
import com.example.amyswateringapp.features.managePlantsFeature.presentation.viewModle.WateringViewModel

fun NavGraphBuilder.appMain(){
    composable(
        route = NavDestinations.AppMain,
    ){
        val hiltViewModel = hiltViewModel<WateringViewModel>()
        WateringAppHome(hiltViewModel)
    }
}