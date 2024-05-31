package com.joaoxstone.stoneroyale.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.joaoxstone.stoneroyale.data.model.player.Badges
import com.joaoxstone.stoneroyale.ui.viewmodel.AppUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgesScreen(
    uiState: AppUiState,
    onClose: (leagueId: Int?, arenaId: Int, title: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val currentBadge = remember { mutableStateOf(Badges()) }
    var showBottomSheet by remember { mutableStateOf(false) }

    val player = uiState.player
    val masteryList = player.badges
    val playerName = player.name

    Scaffold(
        modifier = Modifier,
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        playerName ?: "Badges",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onClose(
                            player.currentPathOfLegendSeasonResult?.leagueNumber,
                            player.arena!!.id!!,
                            player.name!!
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
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
        LazyVerticalGrid(modifier = modifier.padding(innerPadding), columns = GridCells.Fixed(3)) {
            items(masteryList) { badge ->
                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = modifier
                ) {
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
                                modifier = Modifier
                                    .size(10.dp)
                                    .padding(24.dp)
                            )
                        }
                    )
                }
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

@Composable
fun AnimatedLinearProgressIndicator(
    progressBadge: Float, target: Float,
    animationDuration: Int = 1500,
    animationDelay: Int = 200
) {
    var progress by remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        ), label = ""
    )

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (progress < target) {
                println("$progress === $progressBadge")
                println(target)

                if (progress > progressBadge) {
                    println("caiu no breack")
                    break
                }

                progress += (progressBadge * 0.01f)
                delay(100)

            }
        }
    }

    Surface(shape = MaterialTheme.shapes.medium) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )

    }


}