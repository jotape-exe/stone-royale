package com.joaoxstone.stoneroyale.app.viewmodel.chest

import com.joaoxstone.stoneroyale.core.model.chest.UpcomingChests

data class ChestUiState(
    val upcomingChests: UpcomingChests = UpcomingChests(),
    val getUpcomingChests: suspend (tag: String) -> Unit = {},
    val onUpComingChestsChange: (UpcomingChests) -> Unit = {}
)
