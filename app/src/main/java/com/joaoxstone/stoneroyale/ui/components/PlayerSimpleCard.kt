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
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
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
    cardHeader: @Composable () -> Unit,
    cardPlayerContent: @Composable () -> Unit,
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
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
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
}

@Composable
fun RoadAndRankRow(
    modifier: Modifier = Modifier,
    icon: Int,
    actualTrophy: Int,
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

@Composable
fun CardPlayerHead(
    modifier: Modifier = Modifier,
    arenaId: Int,
    UCtrophies: Int,
    trophies: Int,
    playerName: String,
    playerTag: String
) {
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
                modifier = modifier.padding(top = 8.dp),
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

@Composable
fun CardPlayerContent(modifier: Modifier = Modifier, exp: Int, clanName: String, clanTag: String){
    Surface(color = MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium, modifier = modifier.padding(top = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(6.dp)) {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Image(modifier = modifier.size(30.dp), painter = painterResource(id = R.drawable.experience), contentDescription = "experience icon")
                Text(
                    modifier = modifier.padding(bottom = 6.dp),
                    text = "$exp",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Text(modifier = modifier.padding(start = 4.dp), text = "EXP Level", fontWeight = FontWeight.Bold)
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewCP(){
    PlayerSimpleCard(
        cardHeader = {
            CardPlayerHead(
                playerName = "João Pedro",
                playerTag = "#89GOUYLVV",
                arenaId = 18,
                trophies = 9000,
                UCtrophies = 2490
            )
        },
        cardPlayerContent = {
            CardPlayerContent(exp = 56, clanName = "STUNNA", clanTag = "#89asd2")
        }
    )
}