package com.joaoxstone.stoneroyale.app.viewmodel.player

import androidx.lifecycle.ViewModel
import com.joaoxstone.stoneroyale.core.http.ErrorResponses
import com.joaoxstone.stoneroyale.core.http.ResponseBuilder
import com.joaoxstone.stoneroyale.core.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.core.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class PlayerViewModel(repository: PlayerRepository = PlayerRepository()) : ViewModel() {

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
                    var success = false
                    var response = PlayerResponse()
                    var responseMessage = ""

                    try {
                        response = repository.getPlayer(term)
                        this.uiState.value.onPlayerChange(response)
                        success = response.name != null
                    } catch (ex: HttpException) {
                        responseMessage = ErrorResponses.getStatusCodeMessage(ex.code())
                    } catch (ex: UnknownHostException) {
                        responseMessage = ErrorResponses.getStatusCodeMessage(400)
                    } catch (ex: SocketTimeoutException) {
                        responseMessage = ErrorResponses.getStatusCodeMessage(408)
                    } catch (ex: Exception) {
                        responseMessage = ErrorResponses.getStatusCodeMessage(500)
                    }
                    return@copy ResponseBuilder(
                        message = responseMessage,
                        success = success,
                        response = response,
                    )
                },
            )
        }
    }
}