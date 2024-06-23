package com.joaoxstone.stoneroyale.core.repository

import com.joaoxstone.stoneroyale.core.model.clan.ClanResponse
import com.joaoxstone.stoneroyale.core.repository.remote.RetrofitClient
import com.joaoxstone.stoneroyale.core.repository.remote.network.ClashRoyaleService

class ClanRespository {
    private val service: ClashRoyaleService =
        RetrofitClient.getService(ClashRoyaleService::class.java)

    suspend fun getClan(clanTag: String?): ClanResponse {
        return service.getClan(clanTag)
    }
}