package com.joaoxstone.stoneroyale.data.model.player

import com.google.gson.annotations.SerializedName


data class LastPathOfLegendSeasonResult(

    @SerializedName("leagueNumber") var leagueNumber: Int? = null,
    @SerializedName("trophies") var trophies: Int? = null,
    @SerializedName("rank") var rank: String? = null

)