package com.joaoxstone.stoneroyale.app.screens

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.app.components.GitHubButton
import com.joaoxstone.stoneroyale.app.components.common.BottomNavItem
import com.joaoxstone.stoneroyale.app.components.common.BottomNavigation
import com.joaoxstone.stoneroyale.app.screens.chests.ChestsScreen
import com.joaoxstone.stoneroyale.app.screens.clan.ClanScreen
import com.joaoxstone.stoneroyale.app.screens.player.PlayerScreen
import com.joaoxstone.stoneroyale.app.theme.StoneRoyaleTheme
import com.joaoxstone.stoneroyale.app.viewmodel.chest.ChestUiState
import com.joaoxstone.stoneroyale.app.viewmodel.clan.ClanUiState
import com.joaoxstone.stoneroyale.app.viewmodel.player.PlayerUiState


@SuppressLint("RestrictedApi")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    playerUiState: PlayerUiState,
    clanUiState: ClanUiState,
    chestUiState: ChestUiState,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    playerNavigation: (leagueId: Int?, arenaId: Int, title: String) -> Unit,
    clanNavigation: (badgeId: Int?, clanName: String?) -> Unit,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    var tabIndex by rememberSaveable {
        mutableStateOf("player")
    }

    val bottomNavOptions = mapOf(
        "player" to ScreenContent(
            Color(0XFF1f6af2),
            Color(0XFF1650b5),
            R.drawable.player
        ),
        "clan" to ScreenContent(
            Color(0XFFeb7a34),
            Color(0XFFbf6228),
            R.drawable.clanicon
        ),
        "chests" to ScreenContent(
            Color(0xFF66FF55),
            Color(0xFF21C037),
            R.drawable.chest_legendary
        ),
    )

    val rigthColor by animateColorAsState(
        bottomNavOptions[tabIndex]?.rightColor ?: Color.Transparent,
        label = ""
    )
    val leftColor by animateColorAsState(
        bottomNavOptions[tabIndex]?.leftColor ?: Color.Transparent,
        label = ""
    )

    val brush = Brush.horizontalGradient(listOf(leftColor, rigthColor))

    val animatedHeaderScreen = listOf(
        AnimatedScreeenContent(
            route = "player",
            imageId = R.drawable.king
        ),
        AnimatedScreeenContent(
            route = "chests",
            imageId = R.drawable.chest_legendary
        ),
        AnimatedScreeenContent(
            route = "clan",
            imageId = R.drawable.clanicon
        )
    )

    val context = LocalContext.current

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
            }
        ) {

            animatedHeaderScreen.forEach { screen ->
                SetAnimatedContent(
                    imageId = screen.imageId,
                    tabIndex = tabIndex,
                    actualRoute = screen.route
                )
            }

            GitHubButton(
                Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
            )

            Surface(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxHeight(.769f)
                    .fillMaxWidth()
            ) {
                Column {
                    NavHost(
                        navController = navController,
                        startDestination = "player"
                    ) {
                        composable("player") {
                            PlayerScreen(
                                playerUiState = playerUiState,
                                clanUiState = clanUiState,
                                animatedVisibilityScope = animatedContentScope,
                                sharedTransitionScope = sharedTransitionScope,
                                onOpenPlayerProfile = playerNavigation,
                                onOpenClan = { badgeId, clanName ->
                                    clanNavigation(badgeId, clanName)
                                }
                            )
                        }
                        composable("chests") {
                            BackPressHandler(onBackPressed = {
                                screenSwitcher(
                                    route = "clan",
                                    currentRoute = currentRoute,
                                    onNavigate = {
                                        navController.navigate("clan") {
                                            launchSingleTop = true
                                            popUpTo(navController.graph.startDestinationId)
                                        }
                                    }
                                ) {
                                    tabIndex = "clan"
                                }
                            })
                            ChestsScreen(chestUiState = chestUiState)
                        }
                        composable("clan") {
                            BackPressHandler(onBackPressed = {
                                //println(navController.currentBackStack.value.toString())
                                screenSwitcher(
                                    route = "player",
                                    currentRoute = currentRoute,
                                    onNavigate = {
                                        navController.navigate("player") {
                                            launchSingleTop = true
                                            popUpTo(navController.graph.startDestinationId)
                                        }
                                    }
                                ) {
                                    tabIndex = "player"
                                }
                            }
                            )
                            ClanScreen(
                                clanUiState = clanUiState,
                                onOpenDetails = { badgeId, clanName ->
                                    clanNavigation(badgeId, clanName)
                                },
                                animatedContentScope = animatedContentScope,
                                sharedTransitionScope = sharedTransitionScope,
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
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
                                        screenSwitcher(
                                            route = route,
                                            onNavigate = {
                                                navController.navigate(route) {
                                                    launchSingleTop = true
                                                    popUpTo(navController.graph.startDestinationId)
                                                }
                                            }, currentRoute = currentRoute
                                        ) {
                                            tabIndex = route
                                        }
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

@Composable
fun SetAnimatedContent(
    modifier: Modifier = Modifier,
    imageId: Int,
    tabIndex: String,
    actualRoute: String
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        AnimatedVisibility(
            tabIndex == actualRoute,
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "",
                modifier = Modifier.size(230.dp)
            )
        }
    }
}

data class ScreenContent(val leftColor: Color, val rightColor: Color, val imageId: Int)
data class AnimatedScreeenContent(val route: String, @DrawableRes val imageId: Int)

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }
    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}


fun screenSwitcher(
    route: String,
    currentRoute: String?,
    onNavigate: () -> Unit,
    onUpdateIndex: () -> Unit
) {
    onUpdateIndex()
    if (currentRoute != route) {
        onNavigate()
    }
}



