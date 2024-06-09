package com.joaoxstone.stoneroyale.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.data.constants.ClashConstants
import com.joaoxstone.stoneroyale.ui.components.Badge
import com.joaoxstone.stoneroyale.ui.components.BottomNavItem
import com.joaoxstone.stoneroyale.ui.components.BottomNavigation
import com.joaoxstone.stoneroyale.ui.components.ClanSimpleCard
import com.joaoxstone.stoneroyale.ui.components.GitHubButton
import com.joaoxstone.stoneroyale.ui.components.SearchContainer
import com.joaoxstone.stoneroyale.ui.theme.StoneRoyaleTheme
import com.joaoxstone.stoneroyale.ui.viewmodel.AppUiState
import kotlinx.coroutines.launch


@SuppressLint("RestrictedApi")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    uiState: AppUiState, animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    navClick: (leagueId: Int?, arenaId: Int, title: String) -> Unit,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()


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
        /* "cards" to ScreenContent(
             Color(0xFFF21F1F),
             Color(0xFFB51616),
             R.drawable.cardicon
         ),*/
        "clan" to ScreenContent(
            Color(0XFFeb7a34),
            Color(0XFFbf6228),
            R.drawable.clanicon
        ),
        "chests" to ScreenContent(
            Color(0xFF66FF55),
            Color(0xFFEA4DFC),
            R.drawable.chest_legendary
        ),
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

            SetAnimatedContent(
                imageId = R.drawable.king,
                tabIndex = tabIndex,
                actualRoute = "player"
            )
            SetAnimatedContent(
                imageId = R.drawable.chest_legendary,
                tabIndex = tabIndex,
                actualRoute = "chests"
            )
            SetAnimatedContent(
                imageId = R.drawable.clanicon,
                tabIndex = tabIndex,
                actualRoute = "clan"
            )

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
                    NavHost(
                        navController = navController,
                        startDestination = "player"
                    ) {
                        composable("player") {
                            PlayerScreen(
                                uiState = uiState,
                                animatedVisibilityScope = animatedContentScope,
                                sharedTransitionScope = sharedTransitionScope,
                                navClick = navClick
                            )
                        }
                        composable("chests") {
                            //Separate to new Screen
                            Text("Cards")
                        }
                        composable("clan") {
                            ClanScreen(
                                uiState = uiState,
                            )
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
                                        val currentRoute =
                                            navController.currentBackStackEntry?.destination?.route
                                        if (currentRoute != route) {
                                            navController.navigate(route) {
                                                launchSingleTop = true
                                                popUpTo(navController.graph.startDestinationId)
                                            }
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
                modifier = modifier.size(230.dp)
            )
        }
    }
}

data class ScreenContent(val leftColor: Color, val rightColor: Color, val imageId: Int)

