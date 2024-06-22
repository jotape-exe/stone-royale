package com.joaoxstone.stoneroyale.app.model.player

import com.google.gson.annotations.SerializedName


data class PreviousSeason(

    @SerializedName("id") var id: String? = null,
    @SerializedName("trophies") var trophies: Int? = null,
    @SerializedName("bestTrophies") var bestTrophies: Int? = null

)