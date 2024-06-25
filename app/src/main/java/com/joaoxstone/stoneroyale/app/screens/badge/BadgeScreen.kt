package com.joaoxstone.stoneroyale.app.screens.badge

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.app.components.BrokenImage
import com.joaoxstone.stoneroyale.app.viewmodel.player.PlayerUiState
import com.joaoxstone.stoneroyale.core.model.player.Badges

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgesScreen(
    playerUiState: PlayerUiState,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    var isOrderedByLevel by remember {
        mutableStateOf(true)
    }
    var isOrderedByName by remember {
        mutableStateOf(true)
    }
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
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                                leadingIcon = {
                                    Icon(
                                        if (isOrderedByLevel) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                        contentDescription = null
                                    )
                                },
                                text = { Text("Ordenar por nome") },
                                onClick = {
                                    playerUiState.apply {
                                        onPlayerBagdeChange(
                                            if (isOrderedByLevel) {
                                                player.badges.sortedByDescending { it.name }
                                            } else {
                                                player.badges.sortedBy { it.name }
                                            }
                                        )
                                    }
                                    isOrderedByLevel = !isOrderedByLevel
                                }
                            )
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        if (isOrderedByName) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                        contentDescription = null
                                    )
                                },
                                text = { Text("Ordenar por nível") },
                                onClick = {
                                    isOrderedByName = !isOrderedByName
                                    playerUiState.apply {
                                        onPlayerBagdeChange(
                                            if (isOrderedByName) {
                                                playerUiState.player.badges.sortedByDescending { it.level }
                                            } else {
                                                playerUiState.player.badges.sortedBy { it.level }
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                currentBadge.value.apply {
                    ModalBadgeContent(
                        badgeName = name ?: "Não identificado",
                        badgeImage = iconUrls?.large,
                        badgeLevel = level,
                        badgeMaxLevel = maxLevel,
                    )
                }
            }
        }
        LazyVerticalGrid(
            modifier = Modifier.padding(innerPadding),
            columns = GridCells.Fixed(3),
        ) {
            itemsIndexed(masteryList) { _, badge ->
                SubcomposeAsyncImage(
                    modifier = Modifier.clickable(
                        onClick = {
                            showBottomSheet = true
                            currentBadge.value = badge
                        }
                    ),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(badge.iconUrls?.large)
                        .crossfade(true)
                        .build(),
                    contentDescription = badge.name,
                    error = {
                        BrokenImage()
                    },
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(
                                        top = 54.dp,
                                        start = 6.dp
                                    )
                            )
                        }
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
) {

    Row(modifier = modifier.padding(8.dp)) {
        SubcomposeAsyncImage(
            modifier = Modifier.size(104.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(badgeImage)
                .crossfade(true)
                .build(),
            contentDescription = badgeName,
            error = {
                BrokenImage()
            }
        )
        Column {
            Text(
                modifier = Modifier.padding(top = 14.dp),
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
                        modifier = Modifier.padding(10.dp),
                        text = "$badgeLevel / $badgeMaxLevel",
                        fontSize = 12.sp,
                    )
                }
            }

        }
    }
}
