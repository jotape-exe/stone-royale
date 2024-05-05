package com.joaoxstone.stoneroyale.ui.components

import android.graphics.BlurMaskFilter
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.data.constants.ClashConstants

@Composable
fun PlayerSimpleCard(
    cardHeader: @Composable () -> Unit,
    cardPlayerContent: @Composable () -> Unit,
    cardPlayerBottom: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val angle: Float by animateFloatAsState(
        if (isExpanded) 180f else 0f,
        label = "Animation rotation"
    )

    Surface(
        shadowElevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .shadowCustom(
                color = Color(0x540091ff),
                offsetX = 2.dp,
                offsetY = 2.dp,
                blurRadius = 12.dp,
            )
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
            .padding(14.dp)) {

            cardHeader()

            AnimatedVisibility(visible = isExpanded) {
                cardPlayerContent()
            }
            Row {
                Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    IconButton(
                        modifier = modifier.graphicsLayer(rotationZ = angle),
                        onClick = { isExpanded = !isExpanded }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Arrow expand content"
                        )
                    }
                    AnimatedVisibility(visible = isExpanded) {
                        cardPlayerBottom()
                    }
                }

            }
        }
    }
}

@Composable
fun RoadAndRankRow(
    modifier: Modifier = Modifier,
    icon: Int,
    actualTrophy: Int?,
    isUltimateChampion: Boolean
) {
    Row(
        modifier = modifier
            .padding(2.dp)
            .padding(start = 8.dp)
    ) {
        val finalString = StringBuilder()
        finalString.append(actualTrophy)

        Image(
            modifier = modifier.size(22.dp),
            painter = painterResource(icon),
            contentDescription = "arena"
        )

        if (!isUltimateChampion) {
            finalString.append("/9000")
        }

        Text(text = finalString.toString())

    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CardPlayerHead(
    modifier: Modifier = Modifier,
    arenaId: Int,
    leagueNumber: Int?,
    UCtrophies: Int?,
    trophies: Int,
    playerName: String,
    playerTag: String,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    SharedTransitionLayout {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row {
                    Box(modifier = modifier, contentAlignment = Alignment.Center) {
                        Surface(
                            color = Color.Transparent, modifier = modifier
                                .size(50.dp)
                                .shadowCustom(
                                    Color(0x540091ff),
                                    blurRadius = 30.dp,
                                    shapeRadius = 20.dp
                                )
                        ) {
                        }

                        val resource =
                            if (leagueNumber != null) ClashConstants.getIconLeague(leagueNumber) else ClashConstants.getIconArena(
                                arenaId
                            )

                        Image(
                            modifier = modifier
                                .size(62.dp)
                                .sharedElement(
                                    state = rememberSharedContentState(key = "image/$resource"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 600)
                                    }
                                ),
                            painter = painterResource(resource!!),
                            contentDescription = "arena"
                        )
                    }

                    Column {
                        RoadAndRankRow(
                            icon = R.drawable.trophy,
                            actualTrophy = trophies,
                            isUltimateChampion = false
                        )
                        if (UCtrophies !== null && UCtrophies > 0) {
                            RoadAndRankRow(
                                icon = R.drawable.rating,
                                actualTrophy = UCtrophies,
                                isUltimateChampion = true
                            )
                        }


                    }
                }
                Text(
                    modifier = modifier
                        .padding(top = 8.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = "image/$playerName"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 600)
                            }
                        ),
                    text = playerName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Text(modifier = modifier.width(100.dp), fontSize = 14.sp, text = playerTag)
            }
        }
    }

}

@Composable
fun CardPlayerBottom(
    rightSlot: @Composable () -> Unit,
) {
    rightSlot()
}

@Composable
fun CardPlayerContent(
    modifier: Modifier = Modifier,
    exp: Int,
    clanName: String,
    clanTag: String,
    clanIconId: Int? = null
) {
    Column {
        ExpBadge(exp = exp)
        Surface(
            modifier = modifier.padding(top = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(6.dp)) {
                Box(modifier = modifier, contentAlignment = Alignment.Center) {
                    val clanIcon = ClashConstants.getIconClan(clanIconId)
                    clanIcon?.let {
                        Image(
                            modifier = modifier
                                .size(30.dp),
                            painter = painterResource(id = it),
                            contentDescription = "experience icon"
                        )
                    }
                }
                Text(
                    modifier = modifier.padding(start = 4.dp),
                    text = clanName,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ExpBadge(modifier: Modifier = Modifier, exp: Int) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(top = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(6.dp)) {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Image(
                    modifier = modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.experience),
                    contentDescription = "experience icon"
                )
                Text(
                    modifier = modifier.padding(bottom = 6.dp),
                    text = "$exp",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun ProfileAction(modifier: Modifier = Modifier, onclick: () -> Unit) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        FilledTonalIconButton(onClick = { onclick() }) {
            Icon(painter = painterResource(id = R.drawable.dots_three), contentDescription = "More")
        }
    }
}

fun Modifier.shadowCustom(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    shapeRadius: Dp = 0.dp,
) = composed {
    val paint: Paint = remember { Paint() }
    val blurRadiusPx = blurRadius.px(LocalDensity.current)
    val maskFilter = remember {
        BlurMaskFilter(blurRadiusPx, BlurMaskFilter.Blur.NORMAL)
    }
    drawBehind {
        drawIntoCanvas { canvas ->
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = maskFilter
            }
            frameworkPaint.color = color.toArgb()

            val leftPixel = offsetX.toPx()
            val topPixel = offsetY.toPx()
            val rightPixel = size.width + leftPixel
            val bottomPixel = size.height + topPixel

            if (shapeRadius > 0.dp) {
                val radiusPx = shapeRadius.toPx()
                canvas.drawRoundRect(
                    left = leftPixel,
                    top = topPixel,
                    right = rightPixel,
                    bottom = bottomPixel,
                    radiusX = radiusPx,
                    radiusY = radiusPx,
                    paint = paint,
                )
            } else {
                canvas.drawRect(
                    left = leftPixel,
                    top = topPixel,
                    right = rightPixel,
                    bottom = bottomPixel,
                    paint = paint,
                )
            }
        }
    }
}

private fun Dp.px(density: Density): Float =
    with(density) { toPx() }