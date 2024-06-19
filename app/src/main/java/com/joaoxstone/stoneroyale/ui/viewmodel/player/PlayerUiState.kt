package com.joaoxstone.stoneroyale.ui.viewmodel.player

import com.joaoxstone.stoneroyale.data.model.player.Badges
import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse

data class PlayerUiState(
    val player: PlayerResponse = PlayerResponse(),
    val onPlayerChange: (PlayerResponse) -> Unit = {},
    val onPlayerBagdeChange: (List<Badges>) -> Unit = {},
    val onGetPlayer: suspend (term: String) -> Unit = {}
)