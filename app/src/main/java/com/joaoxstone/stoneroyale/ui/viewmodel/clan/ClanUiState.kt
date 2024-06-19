package com.joaoxstone.stoneroyale.ui.viewmodel.clan

import com.joaoxstone.stoneroyale.data.model.clan.ClanResponse

data class ClanUiState(
    val clan: ClanResponse = ClanResponse(),
    val onGetClan: suspend (tag: String) -> Unit = {},
    val onClanChange: (ClanResponse) -> Unit = {}
)