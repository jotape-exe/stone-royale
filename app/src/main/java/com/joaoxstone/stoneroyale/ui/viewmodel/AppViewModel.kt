package com.joaoxstone.stoneroyale.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.joaoxstone.stoneroyale.api
import com.joaoxstone.stoneroyale.data.model.player.Badges
import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AppUiState(
    val player: PlayerResponse = PlayerResponse(),
    val onPlayerChange: (PlayerResponse) -> Unit = {},
    val onPlayerBagdeChange: (List<Badges>) -> Unit = {},
    val onGetPlayer: suspend (term: String) -> Unit = {},
)

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

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
                        var hasPlayer = false
                        try {
                            delay(450)
                            response = api.getPlayer(term)
                            hasPlayer = true
                        } catch (error: Exception) {
                            Log.d("Error: ", error.message.toString())
                        }
                    if (hasPlayer) {
                        this.uiState.value.onPlayerChange(response)
                    }
                }
            )
        }
    }


}