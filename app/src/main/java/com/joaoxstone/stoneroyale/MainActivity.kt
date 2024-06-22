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
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joaoxstone.stoneroyale.data.repository.PlayerRepository
import com.joaoxstone.stoneroyale.ui.screens.BadgesScreen
import com.joaoxstone.stoneroyale.ui.screens.ClanDetailsScreen
import com.joaoxstone.stoneroyale.ui.screens.HomeScreen
import com.joaoxstone.stoneroyale.ui.screens.PlayerProfileScreen
import com.joaoxstone.stoneroyale.ui.screens.WelcomeScreen
import com.joaoxstone.stoneroyale.ui.theme.StoneRoyaleTheme
import com.joaoxstone.stoneroyale.ui.viewmodel.clan.ClanViewModel
import com.joaoxstone.stoneroyale.ui.viewmodel.player.PlayerViewModel


val api = PlayerRepository()

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

                    val playerUiState by playerViewModel.uiState.collectAsState()
                    val clanUiState by clanViewModel.uiState.collectAsState()


                    NavHost(navController = navController, startDestination = "welcome") {
                        composable("welcome") {
                            WelcomeScreen(
                                navigationAction = {
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
                                playerNavigation = { leagueId, arenaId, playerName ->
                                    navController.navigate("profile/${leagueId ?: arenaId}/$playerName")
                                },
                                clanNavigation = { badgeId, clanName ->
                                    navController.navigate("clanDetails/$badgeId/$clanName")
                                },
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable
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


