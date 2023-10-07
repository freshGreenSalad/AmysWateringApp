package com.example.amyswateringapp.common.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.amyswateringapp.features.introduction.presentation.introduction
import com.example.amyswateringapp.features.managePlantsFeature.presentation.appMain

@Composable
fun appState() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavDestinations.introduction){
        introduction{ navController.navigate(NavDestinations.AppMain)}
        appMain()
    }
}

object NavDestinations{
    const val introduction = "Introduction"
    const val AppMain = "AppMain"
}