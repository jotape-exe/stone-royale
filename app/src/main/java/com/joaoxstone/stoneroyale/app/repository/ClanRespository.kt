package com.joaoxstone.stoneroyale.app.repository

import com.joaoxstone.stoneroyale.app.model.clan.ClanResponse
import com.joaoxstone.stoneroyale.app.repository.remote.RetrofitClient
import com.joaoxstone.stoneroyale.app.repository.remote.network.ClashRoyaleService

class ClanRespository {
    val clanService: ClashRoyaleService =
        RetrofitClient.getService(ClashRoyaleService::class.java)
    suspend fun getClan(clanTag: String): ClanResponse {
        return clanService.getClan(clanTag)
    }
}