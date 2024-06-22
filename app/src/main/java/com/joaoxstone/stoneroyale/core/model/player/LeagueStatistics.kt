package com.joaoxstone.stoneroyale.core.model.player

import com.google.gson.annotations.SerializedName


data class LeagueStatistics(

    @SerializedName("currentSeason") var currentSeason: CurrentSeason? = CurrentSeason(),
    @SerializedName("previousSeason") var previousSeason: PreviousSeason? = PreviousSeason(),
    @SerializedName("bestSeason") var bestSeason: BestSeason? = BestSeason()

)