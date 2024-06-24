package com.joaoxstone.stoneroyale.app.screens.clan

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.app.components.clan.ClanDetailsHeader
import com.joaoxstone.stoneroyale.app.components.common.Badge
import com.joaoxstone.stoneroyale.app.components.common.TagBadge
import com.joaoxstone.stoneroyale.app.utils.GlobalUtils
import com.joaoxstone.stoneroyale.app.viewmodel.clan.ClanUiState
import com.joaoxstone.stoneroyale.app.viewmodel.player.PlayerUiState
import com.joaoxstone.stoneroyale.core.constants.ClashConstants
import com.joaoxstone.stoneroyale.core.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.core.repository.PlayerRepository
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
    val playerRepository = PlayerRepository()

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            item {
                var isExpanded by rememberSaveable { mutableStateOf(false) }

                ClanDetailsHeader(
                    Modifier
                        .pointerInput(Unit) {
                            detectDragGestures { _, dragAmount ->
                                if (dragAmount.y > 0) {
                                    isExpanded = true
                                } else if (dragAmount.y < 0) {
                                    isExpanded = false
                                }
                            }
                        }
                        .clickable { isExpanded = !isExpanded },
                    clanDescription = {
                        ClanDescription(
                            description = clanUiState.clan.description!!,
                            isExpanded = isExpanded,
                            onToggle = {
                                isExpanded = !isExpanded
                            }
                        )
                    },
                ) {
                    clanUiState.clan.apply {
                        ClanContent(
                            badgeId = badgeId!!,
                            clanName = name!!,
                            clanTag = tag!!,
                            clanType = type!!.uppercase(),
                            animatedContentScope = animatedContentScope,
                            sharedTransitionScope = sharedTransitionScope
                        )
                    }

                }
            }
            items(clanUiState.clan.memberList) { member ->
                val image = ClashConstants.getIconArena(member.arena!!.id!!)!!
                CardClanMember(
                    modifier = Modifier.padding(4.dp),
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
                                response = playerRepository.getPlayer(member.tag!!)
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
            modifier = Modifier.align(
                Alignment.BottomCenter
            ),
        ) {
            Column {
                Row(modifier = Modifier
                    .clickable(
                        onClick = {
                            onGetPlayer()
                        }
                    )
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Surface(Modifier.size(90.dp), color = Color.Transparent) {

                    }
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = memberName,
                                    modifier = Modifier.padding(bottom = 4.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                                Text(text = memberTag, modifier = Modifier.padding(bottom = 8.dp))
                            }
                            Text(text = "#$memberRank", fontWeight = FontWeight.ExtraBold)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            val timeAgo = GlobalUtils.timeAgo(lastSeen)
                            Badge(
                                text = timeAgo,
                                color = MaterialTheme.colorScheme.background,
                                textColor = MaterialTheme.colorScheme.onBackground,
                                textSize = 14.sp,
                                shape = MaterialTheme.shapes.small
                            )
                            Badge(
                                text = "$memberDonations doações",
                                imageResoure = R.drawable.cr_cards,
                                color = MaterialTheme.colorScheme.background,
                                textColor = MaterialTheme.colorScheme.onBackground,
                                textSize = 14.sp,
                                shape = MaterialTheme.shapes.small
                            )
                        }
                    }
                }
                if (loading.first == memberTag && loading.second) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }
        Image(
            modifier = Modifier
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

@Composable
fun ClanDescription(
    modifier: Modifier = Modifier,
    description: String,
    isExpanded: Boolean = false,
    onToggle: () -> Unit
) {
    val angle: Float by animateFloatAsState(
        if (isExpanded) 180f else 0f,
        label = "Animation rotation"
    )
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = isExpanded) {
            Surface(
                modifier = Modifier.padding(2.dp),
                shape = MaterialTheme.shapes.small,
                color = Color(0x20000000)
            ) {
                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Surface(color = Color.Transparent) {
            IconButton(
                modifier = Modifier.graphicsLayer(rotationZ = angle),
                onClick = { onToggle() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Arrow expand content",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ClanContent(
    modifier: Modifier = Modifier,
    badgeId: Int,
    clanName: String,
    clanTag: String,
    clanType: String,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    val resource = painterResource(
        ClashConstants.getIconClan(
            badgeId
        )!!
    )
    with(sharedTransitionScope) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = clanName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 34.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.sharedBounds(
                        rememberSharedContentState(key = "clanName/${clanName}"),
                        animatedVisibilityScope = animatedContentScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 1000)
                        }
                    )
                )
                TagBadge(tag = clanTag)
                AssistChip(
                    colors = AssistChipDefaults.assistChipColors(
                        labelColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = MaterialTheme.shapes.small,
                    onClick = {

                    },
                    enabled = clanType == "OPEN",
                    label = {
                        Text(text = clanType)
                    }
                )
            }
            Image(
                modifier = Modifier
                    .size(130.dp)
                    .sharedBounds(
                        rememberSharedContentState(key = "badgeId/${badgeId}"),
                        animatedVisibilityScope = animatedContentScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 700)
                        }
                    ),
                painter = resource, contentDescription = null
            )
        }
    }

}