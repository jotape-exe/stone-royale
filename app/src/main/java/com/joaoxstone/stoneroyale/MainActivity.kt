package com.joaoxstone.stoneroyale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joaoxstone.stoneroyale.app.screens.HomeScreen
import com.joaoxstone.stoneroyale.app.screens.WelcomeScreen
import com.joaoxstone.stoneroyale.app.screens.badge.BadgesScreen
import com.joaoxstone.stoneroyale.app.screens.clan.ClanDetailsScreen
import com.joaoxstone.stoneroyale.app.screens.player.PlayerProfileScreen
import com.joaoxstone.stoneroyale.app.theme.StoneRoyaleTheme
import com.joaoxstone.stoneroyale.app.viewmodel.chest.ChestViewModel
import com.joaoxstone.stoneroyale.app.viewmodel.clan.ClanViewModel
import com.joaoxstone.stoneroyale.app.viewmodel.player.PlayerViewModel
import com.joaoxstone.stoneroyale.core.preferences.OnboardingManager

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            StoneRoyaleTheme {
                SharedTransitionLayout {

                    val navController = rememberNavController()

                    val playerViewModel: PlayerViewModel = viewModel()
                    val clanViewModel: ClanViewModel = viewModel()
                    val chestViewModel: ChestViewModel = viewModel()

                    val playerUiState by playerViewModel.uiState.collectAsState()
                    val clanUiState by clanViewModel.uiState.collectAsState()
                    val chestUiState by chestViewModel.uiState.collectAsState()

                    val onboardingManager = remember { OnboardingManager(this@MainActivity) }

                    val startRoute = if (onboardingManager.isFirstTimeLaunch()) "welcome" else "home"

                    NavHost(navController = navController, startDestination = startRoute) {
                        composable("welcome") {
                            WelcomeScreen(
                                navigationAction = {
                                    onboardingManager.setOnboardingComplete()
                                    navController.navigate("home")
                                },
                            )
                        }
                        composable(route = "profile/{leagueId}/{playerName}",
                            arguments = listOf(
                                navArgument("leagueId") {
                                    type = NavType.IntType
                                },
                                navArgument("playerName") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            PlayerProfileScreen(
                                playerUiState = playerUiState,
                                clanUiState = clanUiState,
                                animatedVisibilityScope = this@composable,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                onOpenClan = { badgeId, clanName ->
                                    navController.navigate("clanDetails/$badgeId/$clanName")
                                },
                                onOpenMasteries = {
                                    navController.navigate("badges")
                                }
                            )
                        }
                        composable(route = "clanDetails/{badgeId}/{clanName}",
                            arguments = listOf(
                                navArgument("badgeId") {
                                    type = NavType.IntType
                                },
                                navArgument("clanName") {
                                    type = NavType.StringType
                                }
                            )) {
                            ClanDetailsScreen(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.surface),
                                playerUiState = playerUiState,
                                clanUiState = clanUiState,
                                animatedContentScope = this@composable,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                onOpenPlayerProfile = { leagueId, arenaId, playerName ->
                                    navController.navigate("profile/${leagueId ?: arenaId}/$playerName")
                                }
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                playerUiState = playerUiState,
                                clanUiState = clanUiState,
                                chestUiState = chestUiState,
                                playerNavigation = { leagueId, arenaId, playerName ->
                                    navController.navigate("profile/${leagueId ?: arenaId}/$playerName")
                                },
                                clanNavigation = { badgeId, clanName ->
                                    navController.navigate("clanDetails/$badgeId/$clanName")
                                },
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable,
                            )
                        }
                        composable("badges") {
                            BadgesScreen(
                                playerUiState = playerUiState,
                                onClose = {
                                    navController.popBackStack()
                                })
                        }
                    }
                }
            }
        }
    }
}