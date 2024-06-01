package com.joaoxstone.stoneroyale

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joaoxstone.stoneroyale.data.repository.PlayerRepository
import com.joaoxstone.stoneroyale.ui.screens.BadgesScreen
import com.joaoxstone.stoneroyale.ui.screens.HomeScreen
import com.joaoxstone.stoneroyale.ui.screens.PlayerProfileScreen
import com.joaoxstone.stoneroyale.ui.screens.WelcomeScreen
import com.joaoxstone.stoneroyale.ui.theme.StoneRoyaleTheme
import com.joaoxstone.stoneroyale.ui.viewmodel.AppViewModel


val api = PlayerRepository()

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoneRoyaleTheme {
                SharedTransitionLayout {

                    val navController = rememberNavController()
                    val viewModel: AppViewModel = viewModel()
                    val uiState by viewModel.uiState.collectAsState()

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                uiState = uiState,
                                navigationAction = {
                                    startActivity(
                                        Intent(
                                            applicationContext,
                                            WelcomeScreen::class.java
                                        )
                                    )
                                },
                                navClick = { leagueId, arenaId, playerName ->
                                    navController.navigate("profile/${leagueId ?: arenaId}/$playerName")
                                },
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable
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
                                uiState = uiState,
                                animatedVisibilityScope = this@composable,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                onOpenClan = {
                                },
                                onOpenMasteries ={
                                    navController.navigate("badges")
                                }
                            )
                        }
                        composable("badges") {
                            BadgesScreen(uiState, onClose = {
                                navController.popBackStack()
                            })
                        }
                    }
                }
            }
        }
    }
}


