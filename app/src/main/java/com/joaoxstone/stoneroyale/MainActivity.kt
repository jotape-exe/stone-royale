package com.joaoxstone.stoneroyale

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joaoxstone.stoneroyale.data.repository.PlayerRepository
import com.joaoxstone.stoneroyale.ui.screens.HomeScreen
import com.joaoxstone.stoneroyale.ui.screens.PlayerProfileScreen
import com.joaoxstone.stoneroyale.ui.screens.WelcomeScreen
import com.joaoxstone.stoneroyale.ui.theme.StoneRoyaleTheme


val api = PlayerRepository()

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoneRoyaleTheme {
                SharedTransitionLayout {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                navigationAction = {
                                      startActivity(Intent(applicationContext, WelcomeScreen::class.java))
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
                            val leagueId = it.arguments?.getInt("leagueId") ?: 1
                            val playerName = it.arguments?.getString("playerName") ?: "Jogador"
                            PlayerProfileScreen(
                                leagueId = leagueId,
                                arenaId = leagueId,
                                playerName = playerName,
                                animatedVisibilityScope = this@composable,
                                sharedTransitionScope = this@SharedTransitionLayout
                            )
                        }
                    }
                }
            }
        }
    }
}


