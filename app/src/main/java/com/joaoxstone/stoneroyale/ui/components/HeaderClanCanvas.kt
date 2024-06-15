package com.joaoxstone.stoneroyale.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.ui.theme.StoneRoyaleTheme

@Composable
fun HeaderClanCanvas(modifier: Modifier = Modifier, heightCanvas: Dp = 100.dp, color: Color = MaterialTheme.colorScheme.primary) {
    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(heightCanvas)) {
        val width = size.width
        val height = size.height
        val path = Path().apply {
            moveTo(0f, height * 1f)
            quadraticTo(
                width * 0.2f, height * 1f,
                width * 0.7f, height * .565f
            )
            quadraticTo(
                width * 0.95f, height * 0.3f,
                width, 0.9f
            )
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        drawPath(path = path, color = color)
    }
}

@Composable
@Preview(showBackground = true)
fun Pv(){
    StoneRoyaleTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            HeaderClanCanvas()
        }
    }
}