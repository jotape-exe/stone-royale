package com.joaoxstone.stoneroyale.app.repository.remote.network

import com.joaoxstone.stoneroyale.app.model.clan.ClanResponse
import com.joaoxstone.stoneroyale.app.model.player.PlayerResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ClashRoyaleService {

    @GET("players/{playerTag}")
    suspend fun getPlayer(@Path("playerTag") playerTag: String): PlayerResponse

    @GET("clans/{clanTag}")
    suspend fun getClan(@Path("clanTag") clanTag: String): ClanResponse
}