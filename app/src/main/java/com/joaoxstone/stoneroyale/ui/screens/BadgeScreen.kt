package com.joaoxstone.stoneroyale.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.data.model.player.Badges
import com.joaoxstone.stoneroyale.ui.viewmodel.AppUiState
import com.joaoxstone.stoneroyale.ui.viewmodel.player.PlayerUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgesScreen(
    playerUiState: PlayerUiState,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val sheetState = rememberModalBottomSheetState()
    val currentBadge = remember { mutableStateOf(Badges()) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val player = playerUiState.player
    val masteryList = player.badges
    val playerName = player.name

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(
                        "Emblemas de ${playerName!!}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onClose()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_filter_list),
                                contentDescription = "More"
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Ordenar por nome") },
                                onClick = {
                                    playerUiState.onPlayerBagdeChange(playerUiState.player.badges.sortedBy { it.name })
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Ornedar por nível") },
                                onClick = {
                                    playerUiState.onPlayerBagdeChange(playerUiState.player.badges.sortedBy { it.level })
                                }
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        val gridState = rememberLazyGridState()
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                val (name, level, maxLevel, progress, target, iconUrls) = currentBadge.value
                ModalBadgeContent(
                    badgeName = name!!,
                    badgeImage = iconUrls?.large,
                    badgeLevel = level,
                    badgeMaxLevel = maxLevel,
                    badgeProgress = progress,
                    badgeTarget = target
                )
            }
        }
        LazyVerticalGrid(
            modifier = modifier.padding(innerPadding),
            columns = GridCells.Fixed(3),
            state = gridState
        ) {
            itemsIndexed(masteryList) { _, badge ->
                SubcomposeAsyncImage(
                    modifier = modifier.clickable(
                        onClick = {
                            showBottomSheet = true
                            currentBadge.value = badge
                        }
                    ),
                    model = badge.iconUrls?.large,
                    contentDescription = badge.name,
                    loading = {
                        CircularProgressIndicator(
                            modifier = modifier
                                .size(10.dp)
                                .padding(18.dp)
                        )
                    }
                )
            }


        }
    }
}

@Composable
fun ModalBadgeContent(
    modifier: Modifier = Modifier,
    badgeName: String,
    badgeImage: String?,
    badgeLevel: Int?,
    badgeMaxLevel: Int?,
    badgeProgress: Int?,
    badgeTarget: Int?,
) {

    Row(modifier = modifier.padding(8.dp)) {
        SubcomposeAsyncImage(
            modifier = modifier.size(104.dp),
            model = badgeImage,
            contentDescription = badgeName
        )
        Column {
            Text(
                modifier = modifier.padding(top = 14.dp),
                text = badgeName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            if (badgeLevel != null) {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        modifier = modifier.padding(10.dp),
                        text = "$badgeLevel / $badgeMaxLevel",
                        fontSize = 12.sp,
                    )
                }
            }

        }
    }
}