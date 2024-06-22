package com.joaoxstone.stoneroyale.ui.components.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TagBadge(modifier: Modifier = Modifier, tag: String) {
    Surface(
        modifier = modifier,
        color = Color(0xBF050031),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(8.dp), text = tag, color = Color.White
        )
    }
}