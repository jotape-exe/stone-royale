package com.joaoxstone.stoneroyale.core.repository

import com.joaoxstone.stoneroyale.core.model.chest.UpcomingChests
import com.joaoxstone.stoneroyale.core.repository.remote.RetrofitClient
import com.joaoxstone.stoneroyale.core.repository.remote.network.ClashRoyaleService

class ChestRepository {
    private val service: ClashRoyaleService =
        RetrofitClient.getService(ClashRoyaleService::class.java)

    suspend fun getUpComingChests(playerTag: String): UpcomingChests {
        return service.getUpComingChests(playerTag)
    }
}