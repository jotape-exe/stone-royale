package com.joaoxstone.stoneroyale.data.repository

import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.data.repository.remote.RetrofitClient
import com.joaoxstone.stoneroyale.data.repository.remote.network.ClashRoyaleService

class PlayerRepository() {
    val playerService: ClashRoyaleService =
        RetrofitClient.getService(ClashRoyaleService::class.java)

    suspend fun getPlayer(playerTag: String): PlayerResponse {
        return playerService.getPlayer(playerTag)
    }
}