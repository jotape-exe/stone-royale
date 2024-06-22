package com.joaoxstone.stoneroyale.app.screens.player

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.app.components.BrokenImage
import com.joaoxstone.stoneroyale.app.components.common.Badge
import com.joaoxstone.stoneroyale.app.components.common.TagBadge
import com.joaoxstone.stoneroyale.app.components.player.AsyncBadge
import com.joaoxstone.stoneroyale.app.components.player.ExpBadge
import com.joaoxstone.stoneroyale.app.components.player.shadowCustom
import com.joaoxstone.stoneroyale.app.viewmodel.clan.ClanUiState
import com.joaoxstone.stoneroyale.app.viewmodel.player.PlayerUiState
import com.joaoxstone.stoneroyale.core.constants.ClashConstants
import com.joaoxstone.stoneroyale.core.model.clan.ClanResponse
import com.joaoxstone.stoneroyale.core.model.player.CurrentDeck
import com.joaoxstone.stoneroyale.core.repository.ClanRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerProfileScreen(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    playerUiState: PlayerUiState,
    clanUiState: ClanUiState,
    onOpenClan: (badgeId: Int, clanName: String) -> Unit,
    onOpenMasteries: () -> Unit
) {

    val clanRespository = ClanRespository()

    val scope = rememberCoroutineScope()
    var loadingClan by remember { mutableStateOf(false) }

    val player = playerUiState.player
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
            sharedTransitionScope = sharedTransitionScope,
            isCreator = isCreator?.name != null
        )
        Card(modifier = Modifier.padding(8.dp), shape = MaterialTheme.shapes.large) {

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
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
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
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
        }
        PlayerProfileContent {
            DeckContainer(currentDeck = player.currentDeck)
        }
        PlayerProfileBottom(Modifier.fillMaxWidth()) {
            ClanContainer(
                badgeClan = player.clan?.badgeId,
                clanName = player.clan?.name,
                clanRole = player.role,
                onOpenClan = {
                    scope.launch {
                        loadingClan = true
                        var clan = ClanResponse()
                        clan = clanRespository.getClan(player.clan!!.tag!!)
                        clanUiState.onClanChange(clan)
                        loadingClan = false
                        onOpenClan(
                            clan.badgeId!!,
                            clan.name!!
                        )
                    }
                },
                loadingClan = loadingClan
            )
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
    sharedTransitionScope: SharedTransitionScope,
    isCreator: Boolean
) {
    with(sharedTransitionScope) {
        Surface(
            color = Color.Transparent,
            modifier = modifier.background(
                Brush.horizontalGradient(
                    ClashConstants.getBackgroundByLeague(leagueId)
                ),
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            ),
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Box(modifier = Modifier.size(162.dp), contentAlignment = Alignment.Center) {
                    Surface(
                        color = Color.Transparent, modifier = Modifier
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
                        modifier = Modifier
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
                        modifier = Modifier
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
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row {
                        TagBadge(tag = playerTag)
                        Spacer(modifier = Modifier.padding(2.dp))
                        if (isPro) {
                            ProBadge()
                        }
                    }
                    if (isCreator) {
                        AsyncBadge(
                            text = "Creator",
                            imageURL = "https://api-assets.clashroyale.com/playerbadges/512/Gx7gSrp4LwTmOnxUQdo8z3kBHpp8sZmHtb1sHMQrqYo.png",
                            color = Color(0xFF01971C)
                        )
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
    Card(modifier = modifier.padding(8.dp), shape = MaterialTheme.shapes.large) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            content()
        }
    }

}

@Composable
fun DeckContainer(modifier: Modifier = Modifier, currentDeck: ArrayList<CurrentDeck>) {
    val evoPositios = listOf(0, 1)
    val scope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    val context = LocalContext.current

    Column(
        modifier
            .padding(8.dp)
    ) {
        Card(shape = MaterialTheme.shapes.large) {
            Surface(
                modifier = Modifier.padding(4.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Último Deck utilizado",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    FilledIconButton(
                        shape = MaterialTheme.shapes.medium,
                        onClick = {
                            scope.launch(Dispatchers.IO) {
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
            }
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(4),
            ) {
                itemsIndexed(currentDeck) { _, card ->
                    if (evoPositios.contains(currentDeck.indexOf(card))) {
                        //Sensitive
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(card.iconUrls?.evolutionMedium ?: card.iconUrls!!.medium)
                                .crossfade(true)
                                .build(),
                            contentDescription = card.name,
                            contentScale = ContentScale.Crop,
                            error = { _ ->
                                SubcomposeAsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(card.iconUrls!!.medium)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = card.name,
                                    error = {
                                        BrokenImage()
                                    }
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
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(card.iconUrls!!.medium)
                                .crossfade(true)
                                .build(),
                            contentDescription = card.name,
                            error = {
                                BrokenImage()
                            },
                            loading = {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .padding(24.dp)
                                )
                            }
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
        modifier = modifier,
        color = color.value,
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(8.dp), text = "CRL 2022", color = Color.White
        )
    }
}



@Composable
fun ClanContainer(
    modifier: Modifier = Modifier,
    badgeClan: Int?,
    clanName: String?,
    clanRole: String?,
    loadingClan: Boolean = false,
    onOpenClan: () -> Unit
) {
    val clanIcon = ClashConstants.getIconClan(badgeClan)
    Column(modifier) {
        Row {
            Image(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(id = clanIcon!!),
                contentDescription = "experience icon"
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = clanName ?: "Sem clã",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        if (badgeClan != null) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = clanRole?.uppercase() ?: "",
                fontWeight = FontWeight.Bold
            )
            OutlinedButton(shape = MaterialTheme.shapes.medium, onClick = {
                onOpenClan()
            }) {
                Text(text = "Membros")
                Icon(
                    modifier = Modifier.padding(end = 4.dp, start = 4.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_groups),
                    contentDescription = ""
                )
                AnimatedVisibility(visible = loadingClan) {
                    CircularProgressIndicator(modifier = Modifier.size(22.dp))
                }
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
            modifier = Modifier.size(66.dp),
            painter = painterResource(id = R.drawable.mastery),
            contentDescription = "Mastery"
        )
    }
}