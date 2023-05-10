package com.example.amyswateringapp.features.managePlantsFeature.presentation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.amyswateringapp.Plant
import com.example.amyswateringapp.R
import java.time.LocalDateTime


@Composable
fun plantCard(plant: Plant, waterPlant: () -> Unit) {
    Row {
        plantImage(plant.image)
        Column(Modifier.padding(8.dp)) {
            Text(text = plant.plantName)
            Spacer(modifier = Modifier.height(8.dp))
            Text(modifier = Modifier.width(150.dp), text = whenToNextWater(plant))
        }
        WaterplantButton { waterPlant() }
    }
}


@Composable
fun WaterplantButton(waterplant:()->Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .semantics { testTag = "cloudClick" }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = MaterialTheme.colorScheme.onSurface),
            ) {
                waterplant()
            },
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material.Icon(
            modifier = Modifier.size(80.dp),
            painter = painterResource(id = R.drawable.cloudy_snowing),
            contentDescription = "Water Icon"
        )
    }
}


@Composable
fun plantImage(uri: Uri) {
    if (uri == Uri.EMPTY) {
        androidx.compose.material.Icon(
            modifier = Modifier
                .size(100.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            painter = painterResource(id = R.drawable.picture),
            contentDescription = "photo Icon"
        )
    } else {
        val painter = rememberAsyncImagePainter(model = uri)
        Image(
            modifier = Modifier
                .size(100.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = "Image of Plant"
        )
    }
}

@Composable
fun whenToNextWater(plant: Plant): String{
    val wateringDateLessThenAWeekAway = plant.NextWateringTime.minusDays(7) <= LocalDateTime.now()
    val dayOfWeekToWater =  "Water on " + plant.NextWateringTime.dayOfWeek.toString().lowercase()
    val numberOfDaysUntilWater = "Water in "+(plant.NextWateringTime.dayOfYear - LocalDateTime.now().dayOfYear).toString()

    return if (wateringDateLessThenAWeekAway){dayOfWeekToWater} else {numberOfDaysUntilWater }
}


