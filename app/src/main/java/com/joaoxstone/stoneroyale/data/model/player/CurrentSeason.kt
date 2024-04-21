package com.joaoxstone.stoneroyale.data.model.player

import com.google.gson.annotations.SerializedName


data class CurrentSeason(

    @SerializedName("trophies") var trophies: Int? = null,
    @SerializedName("bestTrophies") var bestTrophies: Int? = null

)