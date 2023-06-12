package com.bangkit.capstone_project.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun BorderBoxAnimation() {
    var expanded by remember { mutableStateOf(false) }

    val size by animateDpAsState(
        targetValue = if (expanded) 400.dp else 100.dp,
        animationSpec = tween(durationMillis = 500)
    )

    val strokeWidth by animateDpAsState(
        targetValue = if (expanded) 4.dp else 2.dp,
        animationSpec = tween(durationMillis = 500)
    )

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val canvasSize = size.value
        val halfCanvasSize = canvasSize / 2
        val strokeSize = strokeWidth.value

        val left = (size.value / 2) - halfCanvasSize
        val top = (size.value / 2) - halfCanvasSize
        val right = (size.value / 2) + halfCanvasSize
        val bottom = (size.value / 2) + halfCanvasSize

        val rectSize = Size(canvasSize, canvasSize)
        val rectOffset = Offset(left, top)

        val stroke = Stroke(strokeSize)

        drawRect(
            color = Color.Transparent,
            style = stroke,
            topLeft = rectOffset,
            size = rectSize
        )
    }

    LaunchedEffect(Unit) {
        expanded = true
        // Wait for the animation to complete
        delay(500)
        expanded = false
    }
}

@Preview
@Composable
fun animPrev() {
    BorderBoxAnimation()
}