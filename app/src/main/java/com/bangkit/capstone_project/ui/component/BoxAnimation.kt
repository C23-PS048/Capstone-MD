package com.bangkit.capstone_project.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
@Composable

fun OutlineBoxWithCircle(size: Dp) {
    val strokeWidth = 4.dp
    val circleSize = size * 1.5f
    val circleRadius = circleSize / 2
    val boxSize = size - strokeWidth

    Canvas(modifier = Modifier.size(size)) {
        val topLeft = Offset(strokeWidth.value, strokeWidth.value)
        val topRight = Offset(boxSize.value, strokeWidth.value)
        val bottomRight = Offset(boxSize.value, boxSize.value)
        val bottomLeft = Offset(strokeWidth.value, boxSize.value)

        drawRoundRect(
            color = Color.White,
            topLeft = topLeft,
            size = Size(boxSize.value, boxSize.value),
            cornerRadius = CornerRadius(8.dp.toPx()),
            style = Stroke(strokeWidth.value)
        )

        drawCircle(
            color = Color.Transparent,
            radius = circleRadius.value,
            center = Offset(size.value / 2, size.value / 2),
            style = Stroke(strokeWidth.value),
            blendMode = BlendMode.Clear
        )
    }
}



@Preview
@Composable
fun BoxS() {
    OutlineBoxWithCircle(40.dp)
}