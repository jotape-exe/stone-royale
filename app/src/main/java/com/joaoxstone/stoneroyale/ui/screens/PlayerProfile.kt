package com.joaoxstone.stoneroyale.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joaoxstone.stoneroyale.data.constants.ClashConstants
import com.joaoxstone.stoneroyale.ui.components.shadowCustom
import com.joaoxstone.stoneroyale.ui.viewmodel.AppUiState
import com.joaoxstone.stoneroyale.ui.viewmodel.AppViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerProfileScreen(
    modifier: Modifier = Modifier,
    leagueId: Int?,
    arenaId: Int,
    playerName: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    uiState: AppUiState
) {
    with(sharedTransitionScope) {

        val player = uiState.player

        Column {
            Surface(
                color = Color.Transparent,
                modifier = Modifier.background(
                    Brush.horizontalGradient(
                        ClashConstants.getBackgroundByLeague(leagueId)
                    ),
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                ),
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
                                .sharedBounds(
                                    rememberSharedContentState(key = "leagueId/$resource"),
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
                            .sharedBounds(
                                rememberSharedContentState(key = "playerName/$playerName"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 1000)
                                }
                            ),
                        text = playerName,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            player.tag?.let { Text(text = it, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold) }
            player.currentDeck.let { deck->
                deck.forEach { card->
                    Text(text = card.name!!, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold)
                }
            }
        }
        
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerProfileScreenPV() {

}