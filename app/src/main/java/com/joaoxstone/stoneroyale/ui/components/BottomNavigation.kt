package com.joaoxstone.stoneroyale.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.R

data class BottomNavOption(val id: Int)

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier.padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier.padding(4.dp)) {
            content()
        }
    }
}

@Composable
fun BottomNavItem(click: () -> Unit, index: Int, tabIndex: Int, imageId: Int){
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = if (tabIndex == index) Color(0x6FFFFFFF) else Color.Transparent,
                contentColor = LocalContentColor.current,
            ),
            onClick = {
                click()
            }, modifier = Modifier.size(58.dp)
        ) {
            Image(
                modifier = Modifier.padding(8.dp),
                painter = painterResource(id = imageId),
                contentDescription = null,
            )
        }
}