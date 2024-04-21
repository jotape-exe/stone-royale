package com.joaoxstone.stoneroyale.data.repository.remote.network

import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ClashRoyaleService {

    @GET("players/{playerTag}")
    suspend fun getPlayer(@Path("playerTag") playerTag: String): PlayerResponse
}