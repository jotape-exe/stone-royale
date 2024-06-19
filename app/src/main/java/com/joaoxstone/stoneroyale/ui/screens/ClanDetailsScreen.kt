package com.joaoxstone.stoneroyale.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.data.constants.ClashConstants
import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.data.repository.PlayerRepository
import com.joaoxstone.stoneroyale.ui.utils.DateUtils
import com.joaoxstone.stoneroyale.ui.viewmodel.AppUiState
import com.joaoxstone.stoneroyale.ui.viewmodel.clan.ClanUiState
import com.joaoxstone.stoneroyale.ui.viewmodel.player.PlayerUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ClanDetailsScreen(
    modifier: Modifier = Modifier,
    playerUiState: PlayerUiState,
    clanUiState: ClanUiState,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    onOpenPlayerProfile: (leagueId: Int?, arenaId: Int, playerName: String) -> Unit,
) {
    val scope: CoroutineScope = rememberCoroutineScope()
    var loading by remember { mutableStateOf("" to false) }
    val playerRespository = PlayerRepository()

    Column(
        modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        with(sharedTransitionScope) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = modifier) {
                item {
                    val resource = painterResource(
                        ClashConstants.getIconClan(
                            clanUiState.clan.badgeId
                        )!!
                    )
                    Row(
                        modifier = modifier.fillMaxWidth(),
                    ) {
                        Text(text = clanUiState.clan.name!!,
                            fontWeight = FontWeight.Bold,
                            fontSize = 34.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = modifier.sharedBounds(
                                rememberSharedContentState(key = "clanName/${clanUiState.clan.name}"),
                                animatedVisibilityScope = animatedContentScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 1000)
                                }
                            ))
                        Image(
                            modifier = modifier
                                .size(130.dp)
                                .sharedBounds(
                                    rememberSharedContentState(key = "badgeId/${clanUiState.clan.badgeId}"),
                                    animatedVisibilityScope = animatedContentScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 700)
                                    }
                                ),
                            painter = resource, contentDescription = null
                        )
                    }

                }
                items(clanUiState.clan.memberList) { member ->
                    val image = ClashConstants.getIconArena(member.arena!!.id!!)!!
                    CardClanMember(
                        image = image,
                        memberName = member.name!!,
                        memberTag = member.tag!!,
                        memberDonations = member.donations!!,
                        lastSeen = member.lastSeen!!,
                        memberRank = member.clanRank!!,
                        onGetPlayer = {
                            scope.launch {
                                loading = Pair(member.tag!!, true)
                                var response = PlayerResponse()
                                try {
                                    delay(450)
                                    response = playerRespository.getPlayer(member.tag!!)
                                    playerUiState.onPlayerChange(response)
                                    response.apply {
                                        onOpenPlayerProfile(
                                            currentPathOfLegendSeasonResult?.leagueNumber,
                                            arena?.id!!,
                                            name!!
                                        )
                                    }
                                } catch (error: Exception) {
                                    Log.d("Error: ", error.message.toString())
                                }
                                loading = Pair(member.tag!!, false)
                            }
                        },
                        loading = loading
                    )
                }
            }
        }
    }
}

@Composable
fun CardClanMember(
    modifier: Modifier = Modifier,
    image: Int,
    memberName: String,
    memberTag: String,
    memberDonations: Int,
    lastSeen: String,
    memberRank: Int,
    onGetPlayer: () -> Unit,
    loading: Pair<String, Boolean> = "" to false
) {
    Box(modifier = modifier) {
        Card(
            modifier = modifier.align(
                Alignment.BottomCenter
            ),
        ) {
            Column {
                Row(modifier = modifier
                    .clickable(
                        onClick = {
                            onGetPlayer()
                        }
                    )
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Surface(modifier.size(90.dp), color = Color.Transparent) {

                    }
                    Column {
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = memberName,
                                    modifier = modifier.padding(bottom = 4.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                                Text(text = memberTag, modifier = modifier.padding(bottom = 8.dp))
                            }
                            Text(text = "#$memberRank", fontWeight = FontWeight.ExtraBold)
                        }
                        Column(
                            modifier = modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = "Visto em: ${DateUtils.convertDate(lastSeen)}",
                                    modifier.padding(2.dp)
                                )
                            }
                            Card(
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(text = "Doações: $memberDonations", modifier.padding(2.dp))
                            }
                        }
                    }
                }
                if (loading.first == memberTag && loading.second) {
                    LinearProgressIndicator(modifier = modifier.fillMaxWidth())
                }
            }
        }
        Image(
            modifier = modifier
                .align(
                    Alignment.TopStart
                )
                .size(100.dp),
            painter = painterResource(
                id = image
            ), contentDescription = null
        )
    }
}