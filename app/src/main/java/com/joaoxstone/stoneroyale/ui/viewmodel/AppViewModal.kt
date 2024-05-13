package com.joaoxstone.stoneroyale.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AppUiState(
    val player: PlayerResponse = PlayerResponse(),
    val onPlayerChange: (PlayerResponse) -> Unit = {}
)

class AppViewModal : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onPlayerChange = { player ->
                    _uiState.update { state ->
                        state.copy(player = player)
                    }
                }
            )
        }
    }


}