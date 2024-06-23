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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.joaoxstone.stoneroyale.app.components.clan.ClanSimpleCard
import com.joaoxstone.stoneroyale.app.components.common.Badge
import com.joaoxstone.stoneroyale.app.components.common.EmptyStateScreen
import com.joaoxstone.stoneroyale.app.components.common.SearchContainer
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                if (clanUiState.clan.tag == null) EmptyStateScreen(
                    lastText = " para buscar o clã.",
                    image = R.drawable.cr_red_prince
                )
                AnimatedVisibility(
                    modifier = Modifier.padding(8.dp),
                    visible = clanUiState.clan.name != null
                ) {
                    ClanSimpleCard(
                        cardContent = {
                            Column(Modifier.padding(4.dp)) {
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
                                        enabled = clanUiState.clan.type!!.lowercase() == "open",
                                        label = {
                                            Text(text = clanUiState.clan.type!!.uppercase())
                                        }
                                    )
                                }
                                Text(
                                    modifier = modifier.sharedBounds(
                                        rememberSharedContentState(key = "clanName/${clanUiState.clan.name}"),
                                        animatedVisibilityScope = animatedContentScope,
                                        boundsTransform = { _, _ ->
                                            tween(durationMillis = 700)
                                        }
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    text = clanUiState.clan.name ?: "Clã não encontrado"
                                )
                                Text(
                                    modifier = modifier.padding(top = 8.dp),
                                    fontSize = 18.sp,
                                    text = clanUiState.clan.tag!!
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
                                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Badge(
                                        text = "${clanUiState.clan.members!!}/50 MEMBROS",
                                        textSize = 14.sp,
                                        textColor = MaterialTheme.colorScheme.onBackground,
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = MaterialTheme.shapes.small
                                    )
                                    Badge(
                                        text = clanUiState.clan.location?.name!!.uppercase(),
                                        textSize = 14.sp,
                                        textColor = MaterialTheme.colorScheme.onBackground,
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = MaterialTheme.shapes.small
                                    )
                                }
                                Row(
                                    modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "Requisitos:  ",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Badge(
                                        text = "${clanUiState.clan.requiredTrophies} troféus",
                                        imageResoure = R.drawable.trophy,
                                        color = Color(0xFFE99A00),
                                        textSize = 14.sp,
                                        shape = MaterialTheme.shapes.small
                                    )
                                }
                                OutlinedButton(
                                    shape = MaterialTheme.shapes.medium,
                                    onClick = {
                                        onOpenDetails(
                                            clanUiState.clan.badgeId!!,
                                            clanUiState.clan.name!!
                                        )
                                    }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_groups),
                                        contentDescription = ""
                                    )
                                    Text(
                                        modifier = modifier.padding(start = 6.dp),
                                        text = "Membros "
                                    )
                                }
                            }
                        },
                        imageSlot = {
                            val resource = painterResource(
                                ClashConstants.getIconClan(
                                    clanUiState.clan.badgeId
                                )!!
                            )
                            Image(
                                modifier = modifier.sharedBounds(
                                    rememberSharedContentState(key = "badgeId/${clanUiState.clan.badgeId}"),
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

}