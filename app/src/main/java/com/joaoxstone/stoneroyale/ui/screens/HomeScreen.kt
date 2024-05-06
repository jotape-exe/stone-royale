package com.joaoxstone.stoneroyale.ui.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.api
import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.ui.components.CardPlayerBottom
import com.joaoxstone.stoneroyale.ui.components.CardPlayerContent
import com.joaoxstone.stoneroyale.ui.components.CardPlayerHead
import com.joaoxstone.stoneroyale.ui.components.PlayerSimpleCard
import com.joaoxstone.stoneroyale.ui.components.ProfileAction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    applicationContext: Context,
    navClick: (leagueId: Int?, arenaId: Int, title: String) -> Unit,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var loading by remember { mutableStateOf(false) }
        var player by remember {
            mutableStateOf(PlayerResponse())
        }

        var playerTag by remember { mutableStateOf("") }
        var hasPlayer by remember { mutableStateOf(false) }

        val scope = rememberCoroutineScope()
        Column(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = {
                    applicationContext.startActivity(
                        Intent(
                            applicationContext,
                            WelcomeScreen::class.java
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Welcome")
            }
            OutlinedTextField(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                value = playerTag,
                prefix = {
                    Text(text = "#")
                },
                onValueChange = {
                    playerTag = it
                })


            FilledTonalButton(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                onClick = {
                    scope.launch {
                        loading = true
                        if (playerTag.contains("#")) {
                            Toast.makeText(
                                applicationContext,
                                "Não utilize caracteres especiais",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@launch
                        }
                        val (response, hasPlayerC) = getPlayer(
                            "#${playerTag.uppercase().replace("O", "0").trim()}"
                        )
                        hasPlayer = hasPlayerC
                        if (hasPlayer) {
                            player = response
                        }
                        loading = false
                    }
                }) {
                Text(text = "Buscar")
                Icon(
                    painterResource(id = R.drawable.magnifying_glass),
                    contentDescription = "simbol"
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(64.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
                AnimatedVisibility(player.tag != null) {
                    PlayerSimpleCard(
                        cardHeader = {
                            CardPlayerHead(
                                playerName = player.name!!,
                                playerTag = player.tag!!,
                                arenaId = player.arena?.id!!,
                                trophies = player.trophies!!,
                                UCtrophies = player.currentPathOfLegendSeasonResult?.trophies,
                                leagueNumber = player.currentPathOfLegendSeasonResult?.leagueNumber,
                                sharedTransitionScope = sharedTransitionScope,
                                animatedVisibilityScope = animatedContentScope
                            )
                        },
                        cardPlayerContent = {
                            CardPlayerContent(
                                exp = player.expLevel!!,
                                clanName = player.clan?.name ?: "Sem Clã",
                                clanIconId = player.clan?.badgeId
                            )
                        },
                        cardPlayerBottom = {
                            CardPlayerBottom(rightSlot = {
                                ProfileAction(onclick = {
                                    navClick(
                                        player.currentPathOfLegendSeasonResult?.leagueNumber,
                                        player.arena!!.id!!,
                                        player.name!!
                                    )
                                })
                            })
                        }
                    )
                }

            }
        }
    }
}

suspend fun getPlayer(playerTag: String): Pair<PlayerResponse, Boolean> {
    var response = PlayerResponse()
    var hasPlayerC = false
    try {
        delay(450)
        response = api.getPlayer(playerTag)
        hasPlayerC = true
    } catch (error: Exception) {
        Log.d("Error: ", error.message.toString())
    }
    return response to hasPlayerC
}