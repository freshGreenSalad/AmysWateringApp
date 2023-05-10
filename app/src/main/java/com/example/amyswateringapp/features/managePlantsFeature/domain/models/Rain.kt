package com.example.amyswateringapp.features.managePlantsFeature.domain.models

import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color

data class Rain(
    val rpf: State<Float>,
    val color: Color,
    val y:Float,
    val x: Float
)