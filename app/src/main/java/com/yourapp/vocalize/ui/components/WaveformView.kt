package com.yourapp.vocalize.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawLine
import androidx.compose.ui.unit.dp

@Composable
fun WaveformView(isRecording: Boolean, amplitude: Int = 0) {
    val barCount = 35
    val baseAmplitude = if (isRecording) 0.5f else 0.1f
    val dynamicAmplitude = if (isRecording) (amplitude / 32767f).coerceIn(0f, 1f) else 0f
    val finalAmplitude = baseAmplitude + dynamicAmplitude * 0.5f

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)) {
        val barWidth = size.width / (barCount * 2f)
        for (index in 0 until barCount) {
            val x = barWidth * (index * 2 + 1)
            val progress = kotlin.math.sin((index.toFloat() / barCount) * kotlin.math.PI).toFloat()
            val barHeight = size.height * (0.2f + progress * 0.7f * finalAmplitude)
            drawLine(
                color = Color(0xFFFF6B6B),
                start = androidx.compose.ui.geometry.Offset(x, size.height / 2 - barHeight / 2),
                end = androidx.compose.ui.geometry.Offset(x, size.height / 2 + barHeight / 2),
                strokeWidth = barWidth,
                cap = StrokeCap.Round
            )
        }
    }
}
