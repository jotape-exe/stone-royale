package com.joaoxstone.stoneroyale.data.repository

import com.joaoxstone.stoneroyale.data.model.clan.ClanResponse
import com.joaoxstone.stoneroyale.data.repository.remote.RetrofitClient
import com.joaoxstone.stoneroyale.data.repository.remote.network.ClashRoyaleService

class ClanRespository {
    val clanService: ClashRoyaleService =
        RetrofitClient.getService(ClashRoyaleService::class.java)
    suspend fun getClan(clanTag: String): ClanResponse {
        return clanService.getClan(clanTag)
    }
}