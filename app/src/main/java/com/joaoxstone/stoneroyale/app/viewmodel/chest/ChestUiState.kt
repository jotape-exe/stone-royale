package com.joaoxstone.stoneroyale.app.viewmodel.chest

import com.joaoxstone.stoneroyale.core.http.ResponseBuilder
import com.joaoxstone.stoneroyale.core.model.chest.UpcomingChests

data class ChestUiState(
    val upcomingChests: UpcomingChests = UpcomingChests(),
    val getUpcomingChests: suspend (tag: String) -> ResponseBuilder<UpcomingChests> = {
        ResponseBuilder()
    },
    val onUpComingChestsChange: (UpcomingChests) -> Unit = {}
)
