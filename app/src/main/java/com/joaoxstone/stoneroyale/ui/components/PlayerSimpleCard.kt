package com.joaoxstone.stoneroyale.ui.components

import android.graphics.BlurMaskFilter
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.data.constants.ClashConstants

@Composable
fun PlayerCard(
    modifier: Modifier = Modifier,
    cardHeader: @Composable () -> Unit,
    cardContent: @Composable () -> Unit,
    cardBottom: @Composable () -> Unit,
    imageSlot: @Composable () -> Unit
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
                        modifier = modifier.graphicsLayer(rotationZ = angle),
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
                        modifier = modifier
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
                    modifier = modifier.padding(8.dp),
                    text = playerTag,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = modifier.padding(8.dp),
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
                modifier = modifier.size(38.dp),
                painter = painterResource(id = R.drawable.experience),
                contentDescription = "experience icon"
            )
        }
        Text(
            modifier = modifier.padding(bottom = 6.dp),
            text = "$exp",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    text: String?,
    @DrawableRes imageResoure: Int,
    color: Color
) {
    Box(contentAlignment = Alignment.CenterStart) {
        Surface(
            color = color,
            shape = MaterialTheme.shapes.extraLarge,
            modifier = modifier.padding(start = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(start = 30.dp, top = 6.dp, bottom = 6.dp, end = 6.dp)
            ) {
                Text(
                    text = "$text",
                    color = Color.White,
                    fontSize = 18.sp,
                )
            }
        }
        Image(
            modifier = modifier
                .size(36.dp)
                .align(Alignment.CenterStart),
            painter = painterResource(id = imageResoure),
            contentDescription = "experience icon"
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
    Box(contentAlignment = Alignment.CenterStart) {
        Surface(
            color = color,
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(start = 30.dp, top = 6.dp, bottom = 6.dp, end = 6.dp)
            ) {
                Text(
                    text = "$text",
                    color = Color.White,
                    fontSize = 18.sp,
                )
            }
        }
        AsyncImage(
            modifier = modifier
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
