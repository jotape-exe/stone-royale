package com.joaoxstone.stoneroyale.app.viewmodel.clan

import android.util.Log
import androidx.lifecycle.ViewModel
import com.joaoxstone.stoneroyale.core.http.ErrorResponses
import com.joaoxstone.stoneroyale.core.http.ResponseBuilder
import com.joaoxstone.stoneroyale.core.model.clan.ClanResponse
import com.joaoxstone.stoneroyale.core.repository.ClanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ClanViewModel(repository: ClanRepository = ClanRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow(ClanUiState())
    val uiState: StateFlow<ClanUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onClanChange = { clan ->
                    _uiState.update { clanState ->
                        clanState.copy(clan = clan)
                    }
                },
                onGetClan = { term ->
                    var success = false
                    var response = ClanResponse()
                    var responseMessage = ""

                    try {
                        response = repository.getClan(term)
                        this.uiState.value.onClanChange(response)
                        success = response.name != null
                    } catch (ex: HttpException) {
                        responseMessage = ErrorResponses.getStatusCodeMessage(ex.code())
                    } catch (ex: UnknownHostException) {
                        responseMessage = ErrorResponses.getStatusCodeMessage(400)
                    } catch (ex: SocketTimeoutException) {
                        responseMessage = ErrorResponses.getStatusCodeMessage(408)
                    } catch (ex: Exception) {
                        Log.d("Error: ", ex.toString())
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