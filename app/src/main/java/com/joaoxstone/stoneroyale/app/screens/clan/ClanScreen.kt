package com.joaoxstone.stoneroyale.app.screens.clan

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.app.components.clan.CardBottomClan
import com.joaoxstone.stoneroyale.app.components.clan.ClanSimpleCard
import com.joaoxstone.stoneroyale.app.components.common.EmptyStateScreen
import com.joaoxstone.stoneroyale.app.components.common.SearchContainer
import com.joaoxstone.stoneroyale.app.utils.GlobalUtils
import com.joaoxstone.stoneroyale.app.viewmodel.clan.ClanUiState
import com.joaoxstone.stoneroyale.core.constants.ClashConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ClanScreen(
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    clanUiState: ClanUiState,
    onOpenDetails: (Int, String) -> Unit,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    with(sharedTransitionScope) {

        var loading by remember { mutableStateOf(false) }
        var clanTag by remember { mutableStateOf("LL8J2PQ9") }

        Column {
            SearchContainer(
                modifier = modifier
                    .padding(16.dp),
                onSearch = { term ->
                    scope.launch(Dispatchers.IO) {
                        loading = true
                        clanUiState.onGetClan(
                            GlobalUtils.formattedTag(term),
                        )
                        loading = false
                    }
                },
                isLoading = loading,
                color = Color(0xFFE25A01),
                supportingText = "Ex: #LL8J2PQ9",
                label = "Tag do Clã",
                input = clanTag,
                onValueChange = {
                    clanTag = it
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (clanUiState.clan.tag == null) EmptyStateScreen(
                    lastText = " para buscar o clã.",
                    image = R.drawable.cr_red_prince
                )
                AnimatedVisibility(
                    modifier = Modifier.padding(8.dp),
                    visible = clanUiState.clan.name != null
                ) {
                    ClanSimpleCard(
                        cardBottom = {
                            clanUiState.clan.apply {
                                CardBottomClan(
                                    onOpenDetails = {
                                        onOpenDetails(
                                            badgeId!!,
                                            name!!
                                        )
                                    },
                                    clanLocation = location?.name,
                                    clanMembers = members,
                                    requiredTrophies = requiredTrophies,
                                )
                            }

                        },
                        imageSlot = {
                            clanUiState.clan.apply {
                                val resource = painterResource(
                                    ClashConstants.getIconClan(
                                        badgeId
                                    )!!
                                )
                                Image(
                                    modifier = modifier.sharedBounds(
                                        rememberSharedContentState(key = "badgeId/${badgeId}"),
                                        animatedVisibilityScope = animatedContentScope,
                                        boundsTransform = { _, _ ->
                                            tween(durationMillis = 700)
                                        }
                                    ),
                                    painter = resource, contentDescription = name
                                )
                            }

                        }
                    ) {
                        clanUiState.clan.apply {
                            Column(Modifier.padding(10.dp)) {
                                Row(
                                    Modifier
                                        .padding(4.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    AssistChip(
                                        colors = AssistChipDefaults.assistChipColors(
                                            labelColor = MaterialTheme.colorScheme.onPrimary,
                                            containerColor = MaterialTheme.colorScheme.primary
                                        ),
                                        shape = MaterialTheme.shapes.small,
                                        onClick = {

                                        },
                                        enabled = type!!.lowercase() == "open",
                                        label = {
                                            Text(text = type!!.uppercase())
                                        }
                                    )
                                }
                                Text(
                                    modifier = modifier.sharedBounds(
                                        rememberSharedContentState(key = "clanName/${name}"),
                                        animatedVisibilityScope = animatedContentScope,
                                        boundsTransform = { _, _ ->
                                            tween(durationMillis = 700)
                                        }
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    text = name ?: "Clã não encontrado"
                                )
                                Text(
                                    modifier = modifier.padding(top = 8.dp),
                                    fontSize = 18.sp,
                                    text = tag!!
                                )
                            }
                        }

                    }
                }
            }

        }
    }

}