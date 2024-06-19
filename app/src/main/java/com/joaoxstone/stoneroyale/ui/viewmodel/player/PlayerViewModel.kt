package com.joaoxstone.stoneroyale.ui.viewmodel.player

import android.util.Log
import androidx.lifecycle.ViewModel
import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.data.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerViewModel(private val playerRepository: PlayerRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onGetPlayer = { term ->
                    var response = PlayerResponse()
                    try {
                        response = playerRepository.getPlayer(term)
                        _uiState.update { it.copy(player = response) }
                    } catch (error: Exception) {
                        Log.d("Error: ", error.message.toString())
                    }
                }
            )
        }
    }
}