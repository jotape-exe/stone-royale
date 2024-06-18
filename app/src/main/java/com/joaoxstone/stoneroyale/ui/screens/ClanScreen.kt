package com.joaoxstone.stoneroyale.ui.screens

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
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
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
import com.joaoxstone.stoneroyale.data.constants.ClashConstants
import com.joaoxstone.stoneroyale.ui.components.Badge
import com.joaoxstone.stoneroyale.ui.components.ClanSimpleCard
import com.joaoxstone.stoneroyale.ui.components.SearchContainer
import com.joaoxstone.stoneroyale.ui.viewmodel.AppUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ClanScreen(
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    uiState: AppUiState,
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
                    scope.launch (Dispatchers.IO)  {
                        loading = true
                        uiState.onGetClan(
                            "#${
                                term.uppercase().replace("O", "0").trim()
                            }",
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
            AnimatedVisibility(
                modifier = modifier.padding(8.dp),
                visible = uiState.clan.name != null
            ) {
                ClanSimpleCard(
                    cardContent = {
                        Column(modifier.padding(4.dp)) {
                            Row(
                                modifier
                                    .padding(4.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                AssistChip(
                                    colors = AssistChipDefaults.assistChipColors(
                                        labelColor = MaterialTheme.colorScheme.onPrimary,
                                        containerColor = MaterialTheme.colorScheme.primary
                                    ),
                                    shape = MaterialTheme.shapes.large,
                                    onClick = { },
                                    label = {
                                        Text(text = uiState.clan.type!!.uppercase())
                                    }
                                )
                            }
                            Text(
                                modifier = modifier.sharedBounds(
                                    rememberSharedContentState(key = "clanName/${uiState.clan.name}"),
                                    animatedVisibilityScope = animatedContentScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 700)
                                    }
                                ),
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                text = uiState.clan.name ?: "Clã não encontrado"
                            )
                            Text(
                                modifier = modifier.padding(top = 8.dp),
                                fontSize = 18.sp,
                                text = uiState.clan.tag!!
                            )
                        }
                    },
                    cardBottom = {
                        Column(
                            modifier.padding(4.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "${uiState.clan.members!!}/50 membros")
                                Text(text = uiState.clan.location?.name!!)
                            }
                            Row(
                                modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Troféus necessários:  ")
                                Badge(
                                    text = "${uiState.clan.requiredTrophies}",
                                    imageResoure = R.drawable.trophy,
                                    color = Color(0xFFE99A00)
                                )
                            }
                            FilledTonalButton(
                                shape = MaterialTheme.shapes.medium,
                                onClick = {
                                    onOpenDetails(
                                        uiState.clan.badgeId!!,
                                        uiState.clan.name!!
                                    )
                                }) {
                                Text(
                                    modifier = modifier.padding(end = 6.dp),
                                    text = "Ver membros "
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_groups),
                                    contentDescription = ""
                                )
                            }
                        }
                    },
                    imageSlot = {
                        val resource = painterResource(
                            ClashConstants.getIconClan(
                                uiState.clan.badgeId
                            )!!
                        )
                        Image(
                            modifier = modifier.sharedBounds(
                                rememberSharedContentState(key = "badgeId/${uiState.clan.badgeId}"),
                                animatedVisibilityScope = animatedContentScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 700)
                                }
                            ),
                            painter = resource, contentDescription = null
                        )
                    })
            }
        }
    }

}