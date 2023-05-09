package com.example.amyswateringapp.common.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

@Composable
fun BodyLargeText() {
    AmysWateringAppTheme {
        Text("Test Text", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun BodyMediumText() {
    Text("Test Text", style = MaterialTheme.typography.bodyMedium)
}

@Preview
@Composable
fun previewBodyLargeText() {
    AmysWateringAppTheme() {
        Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
            Text(text = "Test", style = MaterialTheme.typography.labelSmall)
            Text(text = "Test", style = MaterialTheme.typography.labelMedium)
            Text(text = "Test", style = MaterialTheme.typography.labelLarge)
            Text(text = "Test", style = MaterialTheme.typography.bodySmall)
            Text(text = "Test", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Test", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Test", style = MaterialTheme.typography.titleSmall)
            Text(text = "Test", style = MaterialTheme.typography.titleMedium)
            Text(text = "Test", style = MaterialTheme.typography.titleLarge)
            Text(text = "Test", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Test", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Test", style = MaterialTheme.typography.headlineLarge)
            Text(text = "Test", style = MaterialTheme.typography.displaySmall)
            Text(text = "Test", style = MaterialTheme.typography.displayMedium)
            Text(text = "Test", style = MaterialTheme.typography.displayLarge)
        }
    }
}


