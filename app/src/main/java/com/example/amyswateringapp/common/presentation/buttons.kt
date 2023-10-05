package com.example.amyswateringapp.wateringAppScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.amyswateringapp.common.presentation.theme.AmysWateringAppTheme

@Composable
fun AddPlantFab(addPlant: ()-> Unit) {
    FloatingActionButton(onClick = {addPlant() }) {
        Icon(Icons.Default.Add, "Add Plant Icon")
    }
}

/*@Composable
fun ButtonS(onClick: () -> Unit,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            shape: Shape = ButtonDefaults.shape,
            colors: ButtonColors = ButtonDefaults.buttonColors(),
            elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
            border: BorderStroke? = null,
            contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
            content: @Composable RowScope.() -> Unit){
    Button(
        onClick,
    modifier.ripple,
    enabled,
    shape,
    colors,
    elevation,
    border,
    contentPadding,
    interactionSource,
    content)
}*/
