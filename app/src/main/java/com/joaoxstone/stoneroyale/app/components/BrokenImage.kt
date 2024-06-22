package com.joaoxstone.stoneroyale.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.R

@Composable
fun BrokenImage(modifier: Modifier = Modifier, size: Dp = 136.dp, padding: Dp = 16.dp) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(
            modifier = Modifier
                .size(size)
                .padding(padding),
            painter = painterResource(id = R.drawable.ic_baseline_broken_image),
            contentDescription = "image not found"
        )
    }
}