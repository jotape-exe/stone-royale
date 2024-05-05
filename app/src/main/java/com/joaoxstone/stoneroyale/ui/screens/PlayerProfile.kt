package com.joaoxstone.stoneroyale.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.data.constants.ClashConstants
import com.joaoxstone.stoneroyale.ui.components.shadowCustom

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PlayerProfileScreen(
    modifier: Modifier = Modifier,
    leagueId: Int?,
    arenaId: Int,
    title: String,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Surface(
        shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
        color = Color(0xFF5F06DB),
        contentColor = Color(0xFF5F06DB)
    ) {
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
            Box(modifier = modifier.size(162.dp), contentAlignment = Alignment.Center) {
                Surface(
                    color = Color.Transparent, modifier = modifier
                        .size(50.dp)
                        .shadowCustom(
                            Color(0x744400FF),
                            blurRadius = 30.dp,
                            shapeRadius = 20.dp
                        )
                ) {

                }
                val resource =
                    if (leagueId != null) ClashConstants.getIconLeague(leagueId) else ClashConstants.getIconArena(
                        arenaId
                    )

                Image(
                    modifier = modifier
                        .size(162.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = "image/$resource"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 1000)
                            }
                        ),
                    painter = painterResource(resource!!),
                    contentDescription = "arena"
                )
            }
            Text(
                modifier = modifier
                    .padding(top = 14.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "image/$title"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 600)
                        }
                    ),
                text = title,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}