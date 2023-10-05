package com.example.amyswateringapp.common.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.amyswateringapp.introduction.presentation.Introduction

@Composable
fun appState() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavDestinations.introduction){
        Introduction{ navController.navigate(NavDestinations.AppMain)}
    }
}

object NavDestinations{
    const val introduction = "Introduction"
    const val AppMain = "AppMain"
}