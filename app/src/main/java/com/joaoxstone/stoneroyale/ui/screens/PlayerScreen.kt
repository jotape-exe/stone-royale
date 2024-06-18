package com.joaoxstone.stoneroyale.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.data.constants.ClashConstants
import com.joaoxstone.stoneroyale.ui.components.CardHeader
import com.joaoxstone.stoneroyale.ui.components.CardPlayerContent
import com.joaoxstone.stoneroyale.ui.components.EmptyData
import com.joaoxstone.stoneroyale.ui.components.ImageArenaLeague
import com.joaoxstone.stoneroyale.ui.components.PlayerCard
import com.joaoxstone.stoneroyale.ui.components.ProfileAction
import com.joaoxstone.stoneroyale.ui.components.SearchContainer
import com.joaoxstone.stoneroyale.ui.viewmodel.AppUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier, uiState: AppUiState,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navClick: (Int?, Int, String) -> Unit,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    var playerTag by remember { mutableStateOf("89G0UYLVV") }
    var loading by remember { mutableStateOf(false) }
    val player = uiState.player

    Column {
        SearchContainer(
            modifier = modifier
                .padding(16.dp),
            onSearch = { term ->
                scope.launch(Dispatchers.IO) {
                    loading = true
                    uiState.onGetPlayer(
                        "#${
                            term.uppercase().replace("O", "0").trim()
                        }",
                    )
                    loading = false
                }
            },
            isLoading = loading,
            supportingText = "Ex: #G9YV9GR8R",
            label = "Tag do Jogador",
            input = playerTag,
            onValueChange = {
                playerTag = it
            }
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (player.name.isNullOrEmpty()) EmptyData()
            AnimatedVisibility(
                visible = !uiState.player.name.isNullOrEmpty(),
            ) {
                PlayerCard(
                    imageSlot = {
                        val league =
                            player.currentPathOfLegendSeasonResult?.leagueNumber
                        val imageResourceArenaLeague =
                            if (league !== null) ClashConstants.getIconLeague(
                                league
                            ) else ClashConstants.getIconArena(
                                player.arena!!.id!!
                            )
                        ImageArenaLeague(
                            imageResourceArenaLeague!!,
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    },
                    cardHeader = {
                        CardHeader(
                            playerName = player.name!!,
                            playerTag = player.tag!!,
                            trophies = player.trophies!!,
                            UCtrophies = player.currentPathOfLegendSeasonResult?.trophies,
                            leagueNumber = player.currentPathOfLegendSeasonResult?.leagueNumber,
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    },
                    cardContent = {
                        val classicChallengeWins =
                            player.badges.find { badge ->
                                badge.name?.lowercase()
                                    .equals("classic12wins")
                            }
                        val grandChallengeWins =
                            player.badges.find { badge ->
                                badge.name?.lowercase()
                                    .equals("grand12wins")
                            }
                        CardPlayerContent(
                            exp = player.expLevel!!,
                            classicChallengWins = classicChallengeWins?.progress,
                            grandChallengWins = grandChallengeWins?.progress,
                            challengeWins = player.challengeMaxWins
                        )
                    },
                    cardBottom = {
                        val clanIcon =
                            ClashConstants.getIconClan(player.clan?.badgeId)
                        val clanContent = @Composable {
                            Image(
                                modifier = Modifier
                                    .size(30.dp),
                                painter = painterResource(id = clanIcon!!),
                                contentDescription = "experience icon"
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = player.clan?.name ?: "Sem clã",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        player.clan?.let { clan ->
                            if (clan.name.isNullOrEmpty()) {
                                TextButton(onClick = { }, enabled = false) {
                                    clanContent()
                                }
                            } else {
                                OutlinedButton(
                                    shape = MaterialTheme.shapes.medium,
                                    onClick = { }) {
                                    clanContent()
                                }
                            }
                        }

                        ProfileAction(onclick = {
                            navClick(
                                player.currentPathOfLegendSeasonResult?.leagueNumber,
                                player.arena!!.id!!,
                                player.name!!
                            )
                        })
                    }
                )
            }
        }
    }
}