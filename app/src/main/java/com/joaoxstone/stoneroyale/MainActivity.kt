package com.joaoxstone.stoneroyale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joaoxstone.stoneroyale.data.repository.PlayerRepository
import com.joaoxstone.stoneroyale.ui.screens.HomeScreen
import com.joaoxstone.stoneroyale.ui.screens.PlayerProfileScreen
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
                                applicationContext = this@MainActivity.applicationContext,
                                navClick = { leagueId, arenaId, title ->
                                    navController.navigate("profile/${leagueId ?: arenaId}/$title")
                                },
                                animatedVisibilityScope = this
                            )

                        }
                        composable(route = "profile/{leagueId}/{title}",
                            arguments = listOf(
                                navArgument("leagueId") {
                                    type = NavType.IntType
                                },
                                navArgument("title") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val leagueId = it.arguments?.getInt("leagueId") ?: 1
                            val title = it.arguments?.getString("title") ?: "Jogador"
                            PlayerProfileScreen(leagueId = leagueId, arenaId  = leagueId, title = title, animatedVisibilityScope = this)
                        }
                    }
                }
            }
        }
    }
}


