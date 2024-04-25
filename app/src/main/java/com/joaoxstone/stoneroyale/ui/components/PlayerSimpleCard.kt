package com.joaoxstone.stoneroyale.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.data.constants.ClashConstants
import java.lang.StringBuilder

@Composable
fun PlayerSimpleCard(
    playerName: String,
    playerTag: String,
    arenaId: Int,
    trophies: Int,
    UCtrophies: Int,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val angle: Float by animateFloatAsState(if (isExpanded) 180f else 0f, label = "Animation rotation")

    Surface(
        shadowElevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = modifier
            .fillMaxWidth()
            .clickable {

            }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .padding(15.dp)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row {
                        Image(
                            modifier = modifier.size(42.dp),
                            painter = painterResource(
                                ClashConstants.getIconArena(
                                    arenaId,
                                    UCtrophies > 0
                                )!!
                            ),
                            contentDescription = "arena"
                        )
                        Column {
                            RoadAndRankRow(
                                icon = R.drawable.trophy,
                                actualTrophy = trophies,
                                isUltimateChampion = false
                            )
                            RoadAndRankRow(
                                icon = R.drawable.rating,
                                actualTrophy = UCtrophies,
                                isUltimateChampion = true
                            )
                        }
                    }
                    Text(
                        text = playerName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
                Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.End) {
                    Text(modifier =  modifier.width(90.dp), text = playerTag)
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
            AnimatedVisibility(visible = isExpanded) {
                Text(modifier = modifier.padding(top =20.dp), text = "Olá Estou Visível !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewPlayerSimpleCard() {
    PlayerSimpleCard("João Pedro", "#89GOUYLVV", 54000020, 1900, 1600)
}

@Composable
fun RoadAndRankRow(
    modifier: Modifier = Modifier,
    icon: Int,
    actualTrophy: Int,
    isUltimateChampion: Boolean
) {
    Row {
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