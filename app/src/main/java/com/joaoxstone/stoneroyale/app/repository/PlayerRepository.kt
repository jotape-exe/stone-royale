package com.joaoxstone.stoneroyale.app.repository

import com.joaoxstone.stoneroyale.app.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.app.repository.remote.RetrofitClient
import com.joaoxstone.stoneroyale.app.repository.remote.network.ClashRoyaleService

class PlayerRepository {
    val playerService: ClashRoyaleService =
        RetrofitClient.getService(ClashRoyaleService::class.java)

    suspend fun getPlayer(playerTag: String): PlayerResponse {
        return playerService.getPlayer(playerTag)
    }
}