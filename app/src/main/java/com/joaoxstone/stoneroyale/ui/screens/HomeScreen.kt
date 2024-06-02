package com.joaoxstone.stoneroyale.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.ui.components.BottomNavItem
import com.joaoxstone.stoneroyale.ui.components.BottomNavigation
import com.joaoxstone.stoneroyale.ui.components.EmptyData
import com.joaoxstone.stoneroyale.ui.components.GitHubButton
import com.joaoxstone.stoneroyale.ui.components.SearchContainer
import com.joaoxstone.stoneroyale.ui.theme.StoneRoyaleTheme


class WelcomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            StoneRoyaleTheme {
                MyCompose()
            }
        }
    }
}

@Composable
fun MyCompose() {

    var tabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val bottomNavOptions = mapOf(
        0 to ScreenContent(
            Color(0XFF1f6af2),
            Color(0XFF1650b5),
            R.drawable.player
        ),
        1 to ScreenContent(
            Color(0xFFF21F1F),
            Color(0xFFB51616),
            R.drawable.cardicon
        ),
        2 to ScreenContent(
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
        Box(modifier = Modifier
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
            AnimatedVisibility(tabIndex == 0, modifier = Modifier.align(Alignment.TopCenter),
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                    -fullWidth / 3
                } + fadeIn(
                    animationSpec = tween(durationMillis = 200)
                ),
                exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                    200
                } + fadeOut()) {
                Image(
                    painter = painterResource(id = R.drawable.king),
                    contentDescription = "",
                    modifier = Modifier
                        .size(230.dp)
                )
            }

            AnimatedVisibility(tabIndex == 1, modifier = Modifier.align(Alignment.TopCenter),
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                    -fullWidth / 3
                } + fadeIn(animationSpec = tween(durationMillis = 200)),
                exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) { 200 } + fadeOut()) {
                Image(
                    painter = painterResource(id = R.drawable.prince_red),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(230.dp)
                )
            }

            AnimatedVisibility(tabIndex == 2, modifier = Modifier.align(Alignment.TopCenter),
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                    -fullWidth / 3
                } + fadeIn(animationSpec = tween(durationMillis = 200)),
                exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) { 200 } + fadeOut()) {
                Image(
                    painter = painterResource(id = R.drawable.clanicon),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(200.dp)
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
            ) {
                ConstraintLayout {
                    val (column, navigation, barb) = createRefs()
                    SearchContainer(modifier = Modifier
                        .padding(16.dp)
                        .constrainAs(column) {
                            top.linkTo(parent.top, margin = 16.dp)
                        },
                        onSearch = { term ->
                            print(term)
                        }
                    )
                    EmptyData(modifier = Modifier.constrainAs(barb) {
                        top.linkTo(column.bottom)
                        bottom.linkTo(navigation.top)
                        end.linkTo(column.end)
                        start.linkTo(column.start)
                    })
                    BottomNavigation(
                        modifier = Modifier.constrainAs(navigation) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        }
                    ) {
                        bottomNavOptions.forEach { (key, option) ->
                            BottomNavItem(
                                click = { tabIndex = key },
                                index = key,
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

data class ScreenContent(val leftColor: Color, val rightColor: Color, val imageId: Int)

