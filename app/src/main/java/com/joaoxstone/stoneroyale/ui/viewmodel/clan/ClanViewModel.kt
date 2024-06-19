package com.joaoxstone.stoneroyale.ui.viewmodel.clan

import android.util.Log
import androidx.lifecycle.ViewModel
import com.joaoxstone.stoneroyale.data.model.clan.ClanResponse
import com.joaoxstone.stoneroyale.data.repository.ClanRespository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ClanViewModel(private val clanRespository: ClanRespository) : ViewModel() {
    private val _uiState = MutableStateFlow(ClanUiState())
    val uiState: StateFlow<ClanUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onGetClan = { term ->
                    var response = ClanResponse()
                    try {
                        delay(450)
                        response = clanRespository.getClan(term)
                        _uiState.update { it.copy(clan = response) }
                    } catch (error: Exception) {
                        Log.d("Error: ", error.message.toString())
                    }
                }
            )
        }
    }
}