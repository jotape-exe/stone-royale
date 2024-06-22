package com.joaoxstone.stoneroyale.app.viewmodel.player

import android.util.Log
import androidx.lifecycle.ViewModel
import com.joaoxstone.stoneroyale.core.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.core.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerViewModel : ViewModel() {
    private val playerRepository: PlayerRepository = PlayerRepository()
    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onPlayerChange = { player ->
                    _uiState.update { playerState ->
                        playerState.copy(player = player)
                    }
                },
                onPlayerBagdeChange = {
                    _uiState.update { playerState ->
                        playerState.copy(player = playerState.player.copy(badges = it))
                    }
                },
                onGetPlayer = { term ->
                    var response = PlayerResponse()
                    try {
                        response = playerRepository.getPlayer(term)
                        this.uiState.value.onPlayerChange(response)
                    } catch (error: Exception) {
                        Log.d("Error: ", error.message.toString())
                    }

                },
            )
        }
    }
}