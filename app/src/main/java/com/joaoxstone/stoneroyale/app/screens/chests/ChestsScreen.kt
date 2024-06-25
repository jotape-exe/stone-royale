package com.joaoxstone.stoneroyale.app.screens.chests

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.app.components.common.EmptyStateScreen
import com.joaoxstone.stoneroyale.app.components.common.SearchContainer
import com.joaoxstone.stoneroyale.app.utils.GlobalUtils
import com.joaoxstone.stoneroyale.app.utils.GlobalUtils.makeToast
import com.joaoxstone.stoneroyale.app.viewmodel.chest.ChestUiState
import com.joaoxstone.stoneroyale.core.constants.ClashConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChestsScreen(
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    chestUiState: ChestUiState,
) {

    Column(modifier) {

        var loading by remember { mutableStateOf(false) }
        var playerTag by remember { mutableStateOf("") }
        var isError by remember { mutableStateOf(false) }
        val gridState = rememberLazyGridState()
        val upcomingChests = chestUiState.upcomingChests

        val context = LocalContext.current

        val plainTooltipState by remember {
            mutableStateOf(mutableMapOf<Int, PlainTooltipState>())
        }

        Column(modifier) {
            SearchContainer(
                modifier = modifier
                    .padding(16.dp),
                onSearch = { term ->
                    scope.launch {
                        loading = true
                        val body = chestUiState.getUpcomingChests(GlobalUtils.formattedTag(term))
                        body.apply {
                            isError = !success
                            if(!success){
                                withContext(Dispatchers.Main) {
                                    makeToast(context, message)
                                }
                            }
                        }
                        loading = false
                    }
                },
                isLoading = loading,
                color = Color(0xFF3dd94c),
                supportingText = "Ex: #89G0UYLVV",
                label = "Tag do Jogador",
                input = playerTag,
                onValueChange = {
                    playerTag = it
                },
                isError = isError
            )
            if (upcomingChests.items.size == 0) EmptyStateScreen(
                imageModifier = Modifier.size(210.dp),
                lastText = " para buscar os próximos baús",
                image = R.drawable.magic_archer_2
            )
            AnimatedVisibility(visible = upcomingChests.items.size > 0) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    state = gridState
                ) {
                    itemsIndexed(upcomingChests.items) { index, it ->
                        plainTooltipState[index] = PlainTooltipState()
                        PlainTooltipBox(
                            tooltip = { Text(text = it.name) },
                            tooltipState = plainTooltipState[index]!!,
                        ) {
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        scope.launch {
                                            plainTooltipState[index]!!.show()
                                        }
                                    }
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    modifier = Modifier.size(60.dp),
                                    painter = painterResource(
                                        id = ClashConstants.getChestByChestName(
                                            it.name
                                        )
                                    ),
                                    contentDescription = it.name
                                )
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(end = 24.dp, bottom = 6.dp),
                                    color = MaterialTheme.colorScheme.background,
                                    shape = MaterialTheme.shapes.extraSmall
                                ) {
                                    Text(
                                        text = if (it.index == 0) "próximo" else "+${it.index}",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }


    }
}
