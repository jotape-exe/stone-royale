package com.joaoxstone.stoneroyale.app.viewmodel.clan

import android.util.Log
import androidx.lifecycle.ViewModel
import com.joaoxstone.stoneroyale.core.model.clan.ClanResponse
import com.joaoxstone.stoneroyale.core.repository.ClanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ClanViewModel : ViewModel() {
    private val clanRepository: ClanRepository = ClanRepository()
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
                    var response = ClanResponse()
                    try {
                        response = clanRepository.getClan(term)
                        this.uiState.value.onClanChange(response)
                    } catch (error: Exception) {
                        Log.d("Error: ", error.message.toString())
                    }
                },
            )
        }
    }
}