package com.joaoxstone.stoneroyale.app.components.player

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.app.components.common.Badge
import com.joaoxstone.stoneroyale.core.constants.ClashConstants

@Composable
fun PlayerCard(
    modifier: Modifier = Modifier,
    cardHeader: @Composable () -> Unit,
    cardBottom: @Composable () -> Unit,
    imageSlot: @Composable () -> Unit,
    cardContent: @Composable () -> Unit
) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val angle: Float by animateFloatAsState(
        if (isExpanded) 180f else 0f,
        label = "Animation rotation"
    )

    Box(modifier = modifier) {
        Surface(
            modifier = modifier.padding(top = 18.dp),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = modifier
                .fillMaxWidth()
                .clickable {
                    isExpanded = !isExpanded
                }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {
                cardHeader()
                AnimatedVisibility(visible = isExpanded) {
                    cardContent()
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    AnimatedVisibility(
                        visible = isExpanded,
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            cardBottom()
                        }
                    }

                    IconButton(
                        modifier = Modifier.graphicsLayer(rotationZ = angle),
                        onClick = { isExpanded = !isExpanded }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Arrow expand content"
                        )
                    }

                }
            }
        }
        imageSlot()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CardHeader(
    modifier: Modifier = Modifier,
    leagueNumber: Int?,
    UCtrophies: Int?,
    trophies: Int,
    playerName: String,
    playerTag: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    with(sharedTransitionScope) {
        Column {
            Column(horizontalAlignment = Alignment.End) {
                Surface(
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.medium,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp)
                        .background(
                            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                            brush = Brush.horizontalGradient(
                                ClashConstants.getBackgroundByLeague(leagueNumber)
                            )
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .sharedBounds(
                                rememberSharedContentState(key = "playerName/$playerName"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 1000)
                                }
                            ),
                        text = playerName,
                        color = Color.White,
                        textAlign = TextAlign.End,
                        fontSize = 30.sp
                    )
                }
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = playerTag,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
        }
    }

}


@Composable
fun CardPlayerContent(
    modifier: Modifier = Modifier,
    exp: Int,
    classicChallengWins: Int?,
    grandChallengWins: Int?,
    challengeWins: Int?,
) {
    Column(modifier = modifier.padding(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ExpBadge(exp = exp)
            classicChallengWins?.let {
                Badge(text = "$it x ", imageResoure = R.drawable.cg, color = Color(0XFF59C931))
            }
            grandChallengWins?.let {
                Badge(text = "$it x ", imageResoure = R.drawable.gc, color = Color(0XFFDFAC29))
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
}

@Composable
fun ExpBadge(modifier: Modifier = Modifier, exp: Int) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.medium,
        ) {
            Image(
                modifier = Modifier.size(38.dp),
                painter = painterResource(id = R.drawable.experience),
                contentDescription = "experience icon"
            )
        }
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = "$exp",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AsyncBadge(
    modifier: Modifier = Modifier,
    text: String?,
    imageURL: String,
    color: Color
) {
    Box(modifier, contentAlignment = Alignment.CenterStart) {
        Surface(
            color = color,
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 30.dp, top = 6.dp, bottom = 6.dp, end = 6.dp)
            ) {
                Text(
                    text = "$text",
                    color = Color.White,
                    fontSize = 18.sp,
                )
            }
        }
        AsyncImage(
            modifier = Modifier
                .size(52.dp)
                .padding(end = 20.dp)
                .align(Alignment.CenterStart),
            model = imageURL,
            contentScale = ContentScale.Crop,
            contentDescription = text
        )
    }
}

@Composable
fun ProfileAction(onclick: () -> Unit) {
    FilledIconButton(
        shape = MaterialTheme.shapes.medium,
        onClick = { onclick() }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_person_24),
            contentDescription = "More"
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ImageArenaLeague(
    @DrawableRes resource: Int,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    with(sharedTransitionScope) {
        Image(
            modifier = Modifier
                .size(98.dp)
                .sharedBounds(
                    rememberSharedContentState(key = "leagueId/$resource"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 700)
                    }
                ),
            painter = painterResource(resource),
            contentDescription = "arena"
        )
    }
}
