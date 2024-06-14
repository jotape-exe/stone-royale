package com.joaoxstone.stoneroyale.ui.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.data.constants.ClashConstants
import com.joaoxstone.stoneroyale.ui.viewmodel.AppUiState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ClanDetailsScreen(
    modifier: Modifier = Modifier, uiState: AppUiState,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        with(sharedTransitionScope) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
                item {
                    Column(modifier = modifier.padding(bottom = 20.dp)) {
                        val resource = painterResource(
                            ClashConstants.getIconClan(
                                uiState.clan.badgeId
                            )!!
                        )
                        Text(text = uiState.clan.name!!,
                            fontWeight = FontWeight.Bold,
                            fontSize = 34.sp,
                            modifier = modifier.sharedBounds(
                            rememberSharedContentState(key = "clanName/${uiState.clan.name}"),
                            animatedVisibilityScope = animatedContentScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 1000)
                            }
                        ))
                        Image(
                            modifier = modifier.size(162.dp).sharedBounds(
                                rememberSharedContentState(key = "badgeId/${uiState.clan.badgeId}"),
                                animatedVisibilityScope = animatedContentScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 700)
                                }
                            ),
                            painter = resource, contentDescription = null
                        )
                    }
                }
                items(uiState.clan.memberList) { member ->
                    val image = ClashConstants.getIconArena(member.arena!!.id!!)!!
                    Row {
                        Text(text = member.name!!)
                        Image(
                            contentDescription = member.name!!,
                            modifier = modifier,
                            painter = painterResource(
                                id = image
                            )
                        )
                    }
                }
            }
        }
    }

}