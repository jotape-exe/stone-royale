package com.joaoxstone.stoneroyale.app.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.R

@Composable
fun GitHubButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val url = "https://github.com/jotape-exe/stone-royale.git"
    val gitHubIntent = Intent(Intent.ACTION_VIEW)
    gitHubIntent.setData(Uri.parse(url))

    Surface(
        color = Color.Transparent, modifier = modifier
    ) {
        IconButton(
            onClick = {
                context.startActivity(Intent(gitHubIntent))
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Black,
            )
        ) {
            Image(
                modifier = Modifier.padding(4.dp),
                painter = painterResource(id = R.drawable.github),
                contentDescription = null,
            )
        }
    }
}