package com.joaoxstone.stoneroyale.app.components.clan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.app.components.common.Badge

@Composable
fun ClanSimpleCard(
    modifier: Modifier = Modifier,
    cardBottom: @Composable () -> Unit,
    imageSlot: @Composable () -> Unit,
    cardContent: @Composable () -> Unit,
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
            modifier = Modifier
                .fillMaxHeight(.4f)
                .align(alignment = Alignment.TopCenter)
                .fillMaxWidth(.865f)
                .padding(end = 28.dp, start = 28.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium
        ) {
        }
        Surface(
            modifier = Modifier
                .padding(top = 18.dp)
                .fillMaxWidth(),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                Modifier
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
                cardContent()
                AnimatedVisibility(
                    visible = isExpanded,
                ) {
                    cardBottom()
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {

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
        Row(Modifier.align(Alignment.TopCenter)) {
            imageSlot()
        }
    }
}

@Composable
fun CardBottomClan(
    modifier: Modifier = Modifier,
    clanMembers: Int?,
    clanLocation: String?,
    requiredTrophies: Int?,
    onOpenDetails: () -> Unit
) {
    Column(
        modifier.padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Badge(
                text = "${clanMembers}/50 MEMBROS",
                textSize = 14.sp,
                textColor = MaterialTheme.colorScheme.onBackground,
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            )
            Badge(
                text = clanLocation?.uppercase(),
                textSize = 14.sp,
                textColor = MaterialTheme.colorScheme.onBackground,
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            )
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(
                    text = "Requisitos:  ",
                    fontSize = 10.sp,
                    modifier = Modifier.padding(bottom = 4.dp, start = 2.dp),
                    fontWeight = FontWeight.SemiBold
                )
                Badge(
                    Modifier.padding(bottom = 4.dp),
                    text = "$requiredTrophies troféus",
                    imageResoure = R.drawable.trophy,
                    color = Color(0xFFE99A00),
                    textSize = 14.sp,
                    shape = MaterialTheme.shapes.small
                )
            }

            OutlinedButton(
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    onOpenDetails()
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_groups),
                    contentDescription = clanLocation
                )
                Text(
                    modifier = modifier.padding(start = 6.dp),
                    text = "Membros "
                )
            }
        }
    }
}