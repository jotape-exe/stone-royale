package com.joaoxstone.stoneroyale.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.api
import com.joaoxstone.stoneroyale.data.constants.ClashConstants
import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.ui.components.BottomNavItem
import com.joaoxstone.stoneroyale.ui.components.BottomNavigation
import com.joaoxstone.stoneroyale.ui.components.CardHeader
import com.joaoxstone.stoneroyale.ui.components.CardPlayerContent
import com.joaoxstone.stoneroyale.ui.components.EmptyData
import com.joaoxstone.stoneroyale.ui.components.GitHubButton
import com.joaoxstone.stoneroyale.ui.components.ImageArenaLeague
import com.joaoxstone.stoneroyale.ui.components.PlayerCard
import com.joaoxstone.stoneroyale.ui.components.ProfileAction
import com.joaoxstone.stoneroyale.ui.components.SearchContainer
import com.joaoxstone.stoneroyale.ui.theme.StoneRoyaleTheme
import com.joaoxstone.stoneroyale.ui.viewmodel.AppUiState
import com.joaoxstone.stoneroyale.ui.viewmodel.AppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    uiState: AppUiState, animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    navClick: (leagueId: Int?, arenaId: Int, title: String) -> Unit,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    val player = uiState.player
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var tabIndex by rememberSaveable {
        mutableStateOf("player")
    }

    val bottomNavOptions = mapOf(
        "player" to ScreenContent(
            Color(0XFF1f6af2),
            Color(0XFF1650b5),
            R.drawable.player
        ),
        "cards" to ScreenContent(
            Color(0xFFF21F1F),
            Color(0xFFB51616),
            R.drawable.cardicon
        ),
        "clan" to ScreenContent(
            Color(0XFFeb7a34),
            Color(0XFFbf6228),
            R.drawable.clanicon
        )
    )

    val rigthColor by animateColorAsState(
        bottomNavOptions[tabIndex]?.rightColor ?: Color.Transparent, label = ""
    )
    val leftColor by animateColorAsState(
        bottomNavOptions[tabIndex]?.leftColor ?: Color.Transparent,
        label = ""
    )
    val brush = Brush.horizontalGradient(listOf(leftColor, rigthColor))
    StoneRoyaleTheme {
        Box(modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(brush = brush)
                inset(10f) {
                    drawRect(brush = brush)
                    inset(5f) {
                        drawRect(brush = brush)
                    }
                }
            }) {
            AnimatedVisibility(tabIndex == "player", modifier = modifier.align(Alignment.TopCenter),
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                    -fullWidth / 3
                } + fadeIn(animationSpec = tween(durationMillis = 200)),
                exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                    200
                } + fadeOut())
            {
                Image(
                    painter = painterResource(id = R.drawable.king),
                    contentDescription = "",
                    modifier = modifier
                        .size(230.dp)
                )
            }
            AnimatedVisibility(tabIndex == "cards", modifier = modifier.align(Alignment.TopCenter),
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                    -fullWidth / 3
                } + fadeIn(animationSpec = tween(durationMillis = 200)),
                exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) { 200 } + fadeOut()) {
                Image(
                    painter = painterResource(id = R.drawable.prince_red),
                    contentDescription = "",
                    modifier = modifier
                        .align(Alignment.TopCenter)
                        .size(230.dp)
                )
            }

            AnimatedVisibility(tabIndex ==  "clan", modifier = modifier.align(Alignment.TopCenter),
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                    -fullWidth / 3
                } + fadeIn(animationSpec = tween(durationMillis = 200)),
                exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) { 200 } + fadeOut()) {
                Image(
                    painter = painterResource(id = R.drawable.clanicon),
                    contentDescription = "",
                    modifier = modifier
                        .align(Alignment.TopCenter)
                        .size(200.dp)
                )
            }

            GitHubButton(
                modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
            )

            Surface(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxHeight(.769f)
                    .fillMaxWidth()
            ) {
                Column {
                    SearchContainer(
                        modifier = modifier
                            .padding(16.dp),
                        onSearch = { term ->
                            scope.launch {
                                loading = true
                                val (response, hasPlayerC) = getPlayer(
                                    "#${
                                        term.uppercase().replace("O", "0").trim()
                                    }",
                                )
                                if (hasPlayerC) {
                                    uiState.onPlayerChange(response)
                                }
                                loading = false
                            }
                        },
                        isLoading = loading
                    )
                    NavHost(navController = navController, startDestination = "player") {
                        composable("player") {
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
                                                animatedVisibilityScope = animatedContentScope
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
                                                animatedVisibilityScope = animatedContentScope
                                            )
                                        },
                                        cardContent = {
                                            val classicChallengeWins = player.badges.find { badge ->
                                                badge.name?.lowercase().equals("classic12wins")
                                            }
                                            val grandChallengeWins = player.badges.find { badge ->
                                                badge.name?.lowercase().equals("grand12wins")
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
                                                    OutlinedButton(onClick = { }) {
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
                        composable("cards") {
                            Text("Cards")
                        }
                        composable("clan") {
                            Text("Clan")
                        }
                    }
                    Row(
                        modifier = modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        BottomNavigation {
                            bottomNavOptions.forEach { (route, option) ->
                                BottomNavItem(
                                    click = {
                                        tabIndex = route
                                        navController.navigate(route)
                                    },
                                    index = route,
                                    tabIndex = tabIndex,
                                    imageId = option.imageId
                                )
                            }
                        }
                    }
                }
            }
        }
    }


}

data class ScreenContent(val leftColor: Color, val rightColor: Color, val imageId: Int)

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