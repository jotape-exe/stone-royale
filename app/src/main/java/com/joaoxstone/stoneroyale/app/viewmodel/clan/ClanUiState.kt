package com.joaoxstone.stoneroyale.app.viewmodel.clan

import com.joaoxstone.stoneroyale.core.model.clan.ClanResponse

data class ClanUiState(
    val clan: ClanResponse = ClanResponse(),
    val onGetClan: suspend (tag: String) -> Unit = {},
    val onClanChange: (ClanResponse) -> Unit = {}
)