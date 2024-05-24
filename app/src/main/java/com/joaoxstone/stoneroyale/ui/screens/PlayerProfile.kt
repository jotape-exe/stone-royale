package com.joaoxstone.stoneroyale.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.data.constants.ClashConstants
import com.joaoxstone.stoneroyale.data.model.player.Cards
import com.joaoxstone.stoneroyale.data.model.player.CurrentDeck
import com.joaoxstone.stoneroyale.ui.components.Badge
import com.joaoxstone.stoneroyale.ui.components.ExpBadge
import com.joaoxstone.stoneroyale.ui.components.shadowCustom
import com.joaoxstone.stoneroyale.ui.viewmodel.AppUiState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerProfileScreen(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    uiState: AppUiState
) {
    val player = uiState.player

    val trophies = player.trophies
    val UCtrophies = player.currentPathOfLegendSeasonResult?.trophies
    val exp = player.expLevel!!
    val classicChallengeWins = player.badges.find { badge ->
        badge.name?.lowercase().equals("classic12wins")
    }
    val grandChallengeWins = player.badges.find { badge ->
        badge.name?.lowercase().equals("grand12wins")
    }
    val challengeWins = player.challengeMaxWins

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)) {
        ProfileHeader(
            playerName = player.name!!,
            leagueId = player.currentPathOfLegendSeasonResult?.leagueNumber,
            arenaId = player.arena!!.id!!,
            animatedVisibilityScope = animatedVisibilityScope,
            sharedTransitionScope = sharedTransitionScope
        )
        Row(
            modifier = modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExpBadge(exp = exp)
            Badge(
                text = "$trophies/9000",
                imageResoure = R.drawable.trophy,
                color = Color(0xFFE99A00)
            )
            if (UCtrophies !== null && UCtrophies > 0) {
                Badge(
                    text = "$UCtrophies",
                    imageResoure = R.drawable.rating,
                    color = Color(0xFF6B00BE)
                )
            }
        }
        Row(
            modifier = modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            classicChallengeWins?.let {
                Badge(
                    text = "${it.progress} x ",
                    imageResoure = R.drawable.cg,
                    color = Color(0XFF59C931)
                )
            }
            grandChallengeWins?.let {
                Badge(
                    text = "${it.progress} x ",
                    imageResoure = R.drawable.gc,
                    color = Color(0XFFDFAC29)
                )
            }
            challengeWins?.let {
                if (it > 16) {
                    Badge(
                        text = "$it x ",
                        imageResoure = R.drawable.win20,
                        color = Color(0XFF2946DF)
                    )
                }
            }
        }
        PlayerProfileContent {
            DeckContainer(currentDeck = player.currentDeck!!)
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerProfileScreenPV() {

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    playerName: String,
    leagueId: Int?,
    arenaId: Int,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    with(sharedTransitionScope) {
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
    }
}

@Composable
fun PlayerProfileContent(content: @Composable () -> Unit) {
    Column {
        content()
    }
}

@Composable
fun DeckContainer(modifier: Modifier = Modifier, currentDeck: ArrayList<CurrentDeck>) {
    Column {
        Text(text = "Ultimo Deck utilizado", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Card(modifier = modifier.padding(8.dp), shape = MaterialTheme.shapes.extraLarge) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(
                    8.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp
                )
            ) {
                items(currentDeck) { card ->
                    AsyncImage(
                        model = card.iconUrls!!.medium,
                        contentDescription = card.name
                    )
                }
            }
        }
    }
}