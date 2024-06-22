package com.joaoxstone.stoneroyale.app.components.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    text: String?,
    @DrawableRes imageResoure: Int? = null,
    color: Color = MaterialTheme.colorScheme.background,
    textColor: Color = Color.White,
    textSize: TextUnit = 18.sp,
    shape: CornerBasedShape = MaterialTheme.shapes.extraLarge
) {
    val hasImage = imageResoure != null
    Box(modifier, contentAlignment = Alignment.CenterStart) {
        Surface(
            color = color,
            shape = shape,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            val paddingStart = if (hasImage) 30.dp else 6.dp
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    start = paddingStart,
                    top = 6.dp,
                    bottom = 6.dp,
                    end = 6.dp
                )
            ) {
                Text(
                    text = "$text",
                    color = textColor,
                    fontSize = textSize,
                )
            }
        }
        if (hasImage) {
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.CenterStart),
                painter = painterResource(id = imageResoure!!),
                contentDescription = text
            )
        }

    }
}