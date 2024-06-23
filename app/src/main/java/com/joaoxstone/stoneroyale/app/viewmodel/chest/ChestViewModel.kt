package com.joaoxstone.stoneroyale.app.viewmodel.chest


import android.util.Log
import androidx.lifecycle.ViewModel
import com.joaoxstone.stoneroyale.core.model.chest.UpcomingChests
import com.joaoxstone.stoneroyale.core.repository.ChestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChestViewModel : ViewModel() {
    private val respository: ChestRepository = ChestRepository()
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
                    var response = UpcomingChests()
                    try {
                        response = respository.getUpComingChests(term)
                        this.uiState.value.onUpComingChestsChange(response)
                    } catch (error: Exception) {
                        Log.d("Error: ", error.message.toString())
                    }
                },
            )
        }
    }
}