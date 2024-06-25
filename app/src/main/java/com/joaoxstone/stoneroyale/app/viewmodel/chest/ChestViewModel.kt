package com.joaoxstone.stoneroyale.app.viewmodel.chest


import androidx.lifecycle.ViewModel
import com.joaoxstone.stoneroyale.core.http.ErrorResponses
import com.joaoxstone.stoneroyale.core.http.ResponseBuilder
import com.joaoxstone.stoneroyale.core.model.chest.UpcomingChests
import com.joaoxstone.stoneroyale.core.repository.ChestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ChestViewModel(repository: ChestRepository = ChestRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow(ChestUiState())
    val uiState: StateFlow<ChestUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onUpComingChestsChange = { upcomingChests ->
                    _uiState.update { chestState ->
                        chestState.copy(upcomingChests = upcomingChests)
                    }
                },
                getUpcomingChests = { term ->

                    var success = false
                    var response = UpcomingChests()
                    var responseMessage = ""

                    try {
                        response = repository.getUpComingChests(term)
                        success = response.items.size > 0
                        this.uiState.value.onUpComingChestsChange(response)
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