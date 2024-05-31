package com.joaoxstone.stoneroyale.ui.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.data.constants.ClashConstants
import com.joaoxstone.stoneroyale.data.model.player.CurrentDeck
import com.joaoxstone.stoneroyale.ui.components.AsyncBadge
import com.joaoxstone.stoneroyale.ui.components.Badge
import com.joaoxstone.stoneroyale.ui.components.ExpBadge
import com.joaoxstone.stoneroyale.ui.components.shadowCustom
import com.joaoxstone.stoneroyale.ui.viewmodel.AppUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerProfileScreen(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    uiState: AppUiState,
    onOpenClan: () -> Unit,
    onOpenMasteries: () -> Unit
) {
    val player = uiState.player

    val trophies = player.trophies
    val UCtrophies = player.currentPathOfLegendSeasonResult?.trophies
    val exp = player.expLevel!!
    val classicChallengeWins = player.badges.find { badge ->
        badge.name?.lowercase().equals("classic12wins")
    }

    val scope = rememberCoroutineScope()

    val grandChallengeWins = player.badges.find { badge ->
        badge.name?.lowercase().equals("grand12wins")
    }
    val challengeWins = player.challengeMaxWins
    val isPro = player.badges.find { badge ->
        badge.name!!.lowercase() == "crl20wins2022"
    }
    val isCreator = player.badges.find { badge ->
        badge.name!!.lowercase() == "creator"
    }

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ProfileHeader(
            playerName = player.name!!,
            playerTag = player.tag!!,
            isPro = isPro?.name != null,
            leagueId = player.currentPathOfLegendSeasonResult?.leagueNumber,
            arenaId = player.arena!!.id!!,
            animatedVisibilityScope = animatedVisibilityScope,
            sharedTransitionScope = sharedTransitionScope
        )
        isCreator?.let {
            AsyncBadge(
                text = "Creator",
                imageURL = "https://api-assets.clashroyale.com/playerbadges/512/Gx7gSrp4LwTmOnxUQdo8z3kBHpp8sZmHtb1sHMQrqYo.png",
                color = Color(0xFF01971C)
            )
        }
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
            DeckContainer(currentDeck = player.currentDeck)
        }
        PlayerProfileBottom(modifier.fillMaxWidth()) {
            ClanContainer(
                badgeClan = player.clan?.badgeId,
                clanName = player.clan?.name,
                clanRole = player.role,
                onOpenClan = {
                    scope.launch {
                        onOpenClan()
                    }
                })
            MasteryContainer(onOpenMasteries = { onOpenMasteries() })
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    playerName: String,
    playerTag: String,
    leagueId: Int?,
    arenaId: Int,
    isPro: Boolean,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    Log.d("OXE", "$isPro Rapaz")
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

                Column {
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
                    Spacer(modifier = modifier.padding(4.dp))
                    Row {
                        TagBadge(tag = playerTag)
                        Spacer(modifier = modifier.padding(2.dp))
                        if (isPro) {
                            ProBadge()
                        }
                    }
                }

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
fun PlayerProfileBottom(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        content()
    }
}

@Composable
fun DeckContainer(modifier: Modifier = Modifier, currentDeck: ArrayList<CurrentDeck>) {

    val evoPositios = listOf(0, 1)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Último Deck utilizado",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            FilledIconButton(onClick = {
                scope.launch {
                    val URI = onCopyDeck(currentDeck)
                    val intent = Intent(Intent.ACTION_VIEW, URI)
                    context.startActivity(intent)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_content_copy),
                    contentDescription = "copy"
                )
            }
        }
        Card(modifier = modifier.padding(12.dp), shape = MaterialTheme.shapes.large) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
            ) {
                items(currentDeck) { card ->
                    if (evoPositios.contains(currentDeck.indexOf(card))) {
                        SubcomposeAsyncImage(
                            modifier = modifier.shadowCustom(
                                color = Color(0x744400FF),
                                blurRadius = 4.dp,
                                shapeRadius = 20.dp
                            ),
                            model = card.iconUrls?.evolutionMedium ?: card.iconUrls!!.medium,
                            contentDescription = card.name,
                            contentScale = ContentScale.Crop,
                            error = {
                                AsyncImage(
                                    model = card.iconUrls!!.medium,
                                    contentDescription = card.name
                                )
                            },
                            loading = {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .padding(24.dp)
                                )
                            }
                        )
                    } else {
                        AsyncImage(
                            model = card.iconUrls!!.medium,
                            contentDescription = card.name
                        )
                    }
                }
            }
        }
    }
}

fun onCopyDeck(currentDeck: ArrayList<CurrentDeck>): Uri? {
    val URL_PATH = "https://link.clashroyale.com/en/?clashroyale://copyDeck?deck="
    val fullDeckPath = StringBuilder(URL_PATH)

    for ((index, card) in currentDeck.withIndex()) {
        fullDeckPath.append(card.id)
        if (index < currentDeck.size - 1) {
            fullDeckPath.append(";")
        }
    }

    val URI = Uri.parse(fullDeckPath.toString())

    return URI
}

@Composable
fun ProBadge(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val color = infiniteTransition.animateColor(
        initialValue = Color(0xFFffd000),
        targetValue = Color(0xFFff00e1),
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Surface(
        color = color.value,

        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            fontWeight = FontWeight.ExtraBold,
            modifier = modifier
                .padding(8.dp), text = "PRO", color = Color.White
        )
    }
}

@Composable
fun TagBadge(modifier: Modifier = Modifier, tag: String) {
    Surface(
        color = Color(0xBF050031),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            fontWeight = FontWeight.ExtraBold,
            modifier = modifier
                .padding(8.dp), text = tag, color = Color.White
        )
    }
}

@Composable
fun ClanContainer(
    modifier: Modifier = Modifier,
    badgeClan: Int?,
    clanName: String?,
    clanRole: String?,
    onOpenClan: () -> Unit
) {
    val clanIcon = ClashConstants.getIconClan(badgeClan)
    Column {
        Row {
            Image(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(id = clanIcon!!),
                contentDescription = "experience icon"
            )
            Text(
                modifier = modifier.padding(start = 8.dp),
                text = clanName ?: "Sem clã",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        if (badgeClan != null) {
            Text(modifier = modifier.padding(8.dp), text = clanRole ?: "")
            FilledTonalButton(shape = MaterialTheme.shapes.small, onClick = {
                onOpenClan()
            }) {
                Text(modifier = modifier.padding(end = 4.dp), text = "Ver membros")
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_groups),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun MasteryContainer(
    modifier: Modifier = Modifier,
    onOpenMasteries: () -> Unit
) {
    IconButton(modifier = modifier.size(90.dp), onClick = { onOpenMasteries() }) {
        Image(
            modifier = modifier.size(66.dp),
            painter = painterResource(id = R.drawable.mastery),
            contentDescription = "Mastery"
        )
    }
}