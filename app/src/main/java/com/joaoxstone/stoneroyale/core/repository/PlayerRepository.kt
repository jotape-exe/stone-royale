package com.joaoxstone.stoneroyale.core.repository

import com.joaoxstone.stoneroyale.core.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.core.repository.remote.RetrofitClient
import com.joaoxstone.stoneroyale.core.repository.remote.network.ClashRoyaleService

class PlayerRepository {
    private val service: ClashRoyaleService =
        RetrofitClient.getService(ClashRoyaleService::class.java)

    suspend fun getPlayer(playerTag: String): PlayerResponse {
        return service.getPlayer(playerTag)
    }
}