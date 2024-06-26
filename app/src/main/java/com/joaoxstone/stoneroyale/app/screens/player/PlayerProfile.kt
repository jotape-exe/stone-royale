package com.joaoxstone.stoneroyale.app.screens.player

import android.annotation.SuppressLint
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
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
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
import com.joaoxstone.stoneroyale.app.utils.GlobalUtils.makeToast
import com.joaoxstone.stoneroyale.app.viewmodel.clan.ClanUiState
import com.joaoxstone.stoneroyale.app.viewmodel.player.PlayerUiState
import com.joaoxstone.stoneroyale.core.constants.ClashConstants
import com.joaoxstone.stoneroyale.core.model.player.CurrentDeck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    val scope = rememberCoroutineScope()
    var loadingClan by remember { mutableStateOf(false) }

    val context = LocalContext.current

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
        badge.name?.lowercase() == "crl20wins2022"
    }
    val isCreator = player.badges.find { badge ->
        badge.name?.lowercase() == "creator"
    }

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ProfileHeader(
            playerName = player.name,
            playerTag = player.tag,
            isPro = isPro?.name != null,
            leagueId = player.currentPathOfLegendSeasonResult?.leagueNumber,
            arenaId = player.arena?.id,
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

        DeckContainer(currentDeck = player.currentDeck)

        PlayerProfileBottom(Modifier.fillMaxWidth()) {
            println(player.clan?.tag == clanUiState.clan.tag)
            ClanContainer(
                badgeClan = player.clan?.badgeId,
                clanName = player.clan?.name,
                isStackedClan = (player.clan?.tag == clanUiState.clan.tag),
                clanRole = player.role,
                onOpenClan = {
                    scope.launch {
                        loadingClan = true
                        val body = clanUiState.onGetClan(player.clan!!.tag!!)
                        loadingClan = false
                        body.apply {
                            if (!success) {
                                withContext(Dispatchers.Main) {
                                    makeToast(context, message)
                                }
                            } else {
                                response?.let {
                                    onOpenClan(
                                        it.badgeId!!,
                                        it.name!!
                                    )
                                }
                            }
                        }
                    }
                },
                loadingClan = loadingClan
            )
            MasteryContainer(onOpenMasteries = {
                scope.launch {
                    onOpenMasteries()
                }
            })
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    playerName: String?,
    playerTag: String?,
    leagueId: Int?,
    arenaId: Int?,
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
                    val resource =
                        if (leagueId != null) ClashConstants.getIconLeague(leagueId) else ClashConstants.getIconArena(
                            arenaId!!
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
                        text = playerName!!,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row {
                        TagBadge(tag = playerTag!!)
                        Spacer(modifier = Modifier.padding(2.dp))
                        if (isPro) {
                            ProBadge()
                        }
                    }
                    if (isCreator) {
                        AsyncBadge(
                            text = "Creator",
                            imageURL = ClashConstants.CREATOR_BADGE,
                            color = Color(0xFF01971C)
                        )
                    }
                }

            }
        }
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

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckContainer(modifier: Modifier = Modifier, currentDeck: ArrayList<CurrentDeck>) {
    //A Evolução se caracteriza pelos 2 primeiros itens do array (Regras do próprio game)
    val evoPositions = listOf(0, 1)

    val scope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    val context = LocalContext.current


    val plainTooltipState by remember {
        mutableStateOf(mutableMapOf<Int, PlainTooltipState>())
    }

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
                            scope.launch {
                                // Abre o jogo diretamente ao clicar no link, caso contrário irá abrir o browser padrão
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
                itemsIndexed(currentDeck) { index, card ->
                    plainTooltipState[index] = PlainTooltipState()
                    PlainTooltipBox(
                        tooltip = { Text(text = card.name!!) },
                        tooltipState = plainTooltipState[index]!!,
                    ) {
                        SubcomposeAsyncImage(
                            modifier = Modifier.clickable {
                                scope.launch {
                                    plainTooltipState[index]!!.show()
                                }
                            },
                            model = ImageRequest.Builder(LocalContext.current)
                                //Uma carta pode ou não ter o icone de evolução
                                //A Evolução se caracteriza pelos primeiros 2 itens do array
                                //Mas nem todos os primeiros itens são necessáriamente evoluidos
                                .data(
                                    if (evoPositions.contains(currentDeck.indexOf(card))) {
                                        card.iconUrls?.evolutionMedium
                                            ?: card.iconUrls!!.medium // Prioriza a imagem de evolução
                                    } else {
                                        card.iconUrls!!.medium // Imagem normal da carta
                                    }
                                )
                                .crossfade(true)
                                .build(),
                            contentDescription = card.name,
                            contentScale = ContentScale.Crop,
                            error = { _ ->
                                //Caso a image medium não seja carregada (tem chances por conta da constante adição de recursos)
                                //Uso o componente e BrokenImage
                                SubcomposeAsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(card.iconUrls!!.medium)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = card.name,
                                    error = { BrokenImage() }
                                )
                            },
                            loading = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(
                                                top = 54.dp,
                                                start = 6.dp
                                            )
                                    )
                                }
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

    // Ao adicionar os id's das cartas usando este algoritmo ele pode ser copiado diretamente para o jogo
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
    isStackedClan: Boolean,
    loadingClan: Boolean = false,
    onOpenClan: () -> Unit
) {
    val clanIcon = ClashConstants.getIconClan(badgeClan)
    Column(modifier) {
        Row {
            Image(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(id = clanIcon),
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
        if (badgeClan != null && !isStackedClan) {
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
                    contentDescription = "Ver membros"
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