package com.joaoxstone.stoneroyale.app.viewmodel.player

import com.joaoxstone.stoneroyale.core.http.ResponseBuilder
import com.joaoxstone.stoneroyale.core.model.player.Badges
import com.joaoxstone.stoneroyale.core.model.player.PlayerResponse

data class PlayerUiState(
    val player: PlayerResponse = PlayerResponse(),
    val onPlayerChange: (PlayerResponse) -> Unit = {},
    val onPlayerBagdeChange: (List<Badges>) -> Unit = {},
    val onGetPlayer: suspend (term: String) -> ResponseBuilder<PlayerResponse> = {
        ResponseBuilder()
    }
)