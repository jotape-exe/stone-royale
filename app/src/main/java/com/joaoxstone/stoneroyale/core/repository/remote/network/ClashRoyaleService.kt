package com.joaoxstone.stoneroyale.core.repository.remote.network

import com.joaoxstone.stoneroyale.core.model.chest.UpcomingChests
import com.joaoxstone.stoneroyale.core.model.clan.ClanResponse
import com.joaoxstone.stoneroyale.core.model.player.PlayerResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ClashRoyaleService {

    @GET("players/{playerTag}")
    suspend fun getPlayer(@Path("playerTag") playerTag: String): PlayerResponse

    @GET("players/{playerTag}/upcomingchests")
    suspend fun getUpComingChests(@Path("playerTag") playerTag: String): UpcomingChests

    @GET("clans/{clanTag}")
    suspend fun getClan(@Path("clanTag") clanTag: String?): ClanResponse
}