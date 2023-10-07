package com.example.amyswateringapp.features.introduction.presentation

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amyswateringapp.R
import com.example.amyswateringapp.common.presentation.animatedBrush
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun treeAnimation(treeLocation: MutableState<Dp>,  angle: Int, treeComposable: @Composable ()-> Unit ) {
    //val reverse = if(angle > 180){-1} else {1}
    val animatedTreeLocationX = animateDpAsState(animationSpec = tween(450),
        targetValue =  with(LocalDensity.current){
            treeLocation.value.toPx().toDouble() }.div(cos(angle.toDouble())).dp ,
        label = "animateTreeLocation")
    val animatedTreeLocationY = animateDpAsState(
        animationSpec = tween(450),
        targetValue = with(LocalDensity.current){
            treeLocation.value.toPx().toDouble() }.div(sin(angle.toDouble())).dp ,
        label = "animateTreeLocation")
    Box(modifier = Modifier.offset(x = animatedTreeLocationX.value, y = animatedTreeLocationY.value)) {treeComposable()}
    Log.d(
        animatedTreeLocationX.value.toString(),"x")
    Log.d(animatedTreeLocationY.value.toString(),"y")
}
@Composable
fun Tree() {
    animatedBrush(32.sp)
    Icon(
        tint = Color.Unspecified,
        modifier = Modifier
            .height(600.dp)
            .width(400.dp),
        painter = painterResource(id = R.drawable.vector__1_),
        contentDescription = "Tree In Background"
    )
}

