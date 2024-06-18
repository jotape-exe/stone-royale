package com.joaoxstone.stoneroyale.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.joaoxstone.stoneroyale.data.model.clan.ClanResponse
import com.joaoxstone.stoneroyale.data.model.player.Badges
import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.data.repository.PlayerRepository
import com.joaoxstone.stoneroyale.data.repository.ClanRespository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

//refactor to single responsibility principle
data class AppUiState(
    val player: PlayerResponse = PlayerResponse(),
    val onPlayerChange: (PlayerResponse) -> Unit = {},
    val onPlayerBagdeChange: (List<Badges>) -> Unit = {},
    val onGetPlayer: suspend (term: String) -> Unit = {},
    val clan: ClanResponse = ClanResponse(),
    val onClanChange: (ClanResponse) -> Unit = {},
    val onGetClan: suspend (tag: String) -> Unit = {},
)

class AppViewModel : ViewModel() {

    private val clanRespository = ClanRespository()
    private val playerRepository = PlayerRepository()

    //refactor to srp
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        //refactor to srp
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
                            delay(450)
                            response = playerRepository.getPlayer(term)
                            this.uiState.value.onPlayerChange(response)
                        } catch (error: Exception) {
                            Log.d("Error: ", error.message.toString())
                        }

                },
                onClanChange = { clan ->
                    _uiState.update { clanState ->
                        clanState.copy(clan = clan)
                    }
                },
                onGetClan = { term ->
                    var response = ClanResponse()
                    var hasPlayer = false
                    try {
                        delay(450)
                        response = clanRespository.getClan(term)
                        hasPlayer = true
                    } catch (error: Exception) {
                        Log.d("Error: ", error.message.toString())
                    }
                    if (hasPlayer) {
                        this.uiState.value.onClanChange(response)
                    }
                }
            )
        }
    }


}