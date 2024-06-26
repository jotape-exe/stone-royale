package com.joaoxstone.stoneroyale.app.screens.player

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.app.components.common.EmptyStateScreen
import com.joaoxstone.stoneroyale.app.components.common.SearchContainer
import com.joaoxstone.stoneroyale.app.components.player.CardHeader
import com.joaoxstone.stoneroyale.app.components.player.CardPlayerContent
import com.joaoxstone.stoneroyale.app.components.player.ImageArenaLeague
import com.joaoxstone.stoneroyale.app.components.player.PlayerCard
import com.joaoxstone.stoneroyale.app.components.player.ProfileAction
import com.joaoxstone.stoneroyale.app.utils.GlobalUtils
import com.joaoxstone.stoneroyale.app.utils.GlobalUtils.makeToast
import com.joaoxstone.stoneroyale.app.viewmodel.clan.ClanUiState
import com.joaoxstone.stoneroyale.app.viewmodel.player.PlayerUiState
import com.joaoxstone.stoneroyale.core.constants.ClashConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    playerUiState: PlayerUiState,
    clanUiState: ClanUiState,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onOpenPlayerProfile: (leagueId: Int?, arenaId: Int, title: String) -> Unit,
    onOpenClan: (badgeId: Int?, clanName: String?) -> Unit,
    scope: CoroutineScope = rememberCoroutineScope(),
) {

    var playerTag by rememberSaveable { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var loadingClan by remember { mutableStateOf(false) }
    val player = playerUiState.player

    val context = LocalContext.current


    Column(modifier) {
        SearchContainer(
            modifier = Modifier
                .padding(16.dp),
            onSearch = { term ->
                scope.launch {
                    loading = true

                    val body = playerUiState.onGetPlayer(GlobalUtils.formattedTag(term))
                    body.apply {
                        isError = !success
                        if (!success) {
                            withContext(Dispatchers.Main) {
                                makeToast(context, message)
                            }
                        }
                        loading = false
                    }

                }
            },
            isLoading = loading,
            supportingText = "Ex: #G9YV9GR8R",
            label = "Tag do Jogador",
            input = playerTag,
            onValueChange = {
                playerTag = it
            },
            isError = isError
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (player.name.isNullOrEmpty()) EmptyStateScreen(
                lastText = " para buscar o perfil.",
                image = R.drawable.barb
            )
            AnimatedVisibility(
                visible = !playerUiState.player.name.isNullOrEmpty(),
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
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                text = player.clan?.name ?: "Sem clã",
                                fontWeight = FontWeight.Bold
                            )
                            AnimatedVisibility(visible = loadingClan) {
                                CircularProgressIndicator(modifier = Modifier.size(22.dp))
                            }
                        }
                        player.clan?.let { clan ->
                            if (clan.name.isNullOrEmpty()) {
                                TextButton(onClick = { }, enabled = false) {
                                    clanContent()
                                }
                            } else {
                                OutlinedButton(
                                    shape = MaterialTheme.shapes.medium,
                                    onClick = {
                                        scope.launch {
                                            loadingClan = true
                                            val body = clanUiState.onGetClan(clan.tag!!)
                                            body.response?.let { clanResponse ->
                                                clanUiState.onClanChange(clanResponse)
                                                onOpenClan(
                                                    clanResponse.badgeId,
                                                    clanResponse.name
                                                )
                                            }
                                            loadingClan = false
                                        }

                                    }) {
                                    clanContent()
                                }
                            }
                        }
                        ProfileAction(onclick = {
                            onOpenPlayerProfile(
                                player.currentPathOfLegendSeasonResult?.leagueNumber,
                                player.arena!!.id!!,
                                player.name!!
                            )
                        })
                    }
                ) {
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
                }
            }
        }
    }

}