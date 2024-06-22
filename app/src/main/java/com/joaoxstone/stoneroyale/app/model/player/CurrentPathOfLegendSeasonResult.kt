package com.joaoxstone.stoneroyale.app.model.player

import com.google.gson.annotations.SerializedName


data class CurrentPathOfLegendSeasonResult(

    @SerializedName("leagueNumber") var leagueNumber: Int? = null,
    @SerializedName("trophies") var trophies: Int? = null,
    @SerializedName("rank") var rank: String? = null

)