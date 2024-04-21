package com.joaoxstone.stoneroyale.data.model.player

import com.google.gson.annotations.SerializedName


data class BestSeason(

    @SerializedName("id") var id: String? = null,
    @SerializedName("trophies") var trophies: Int? = null

)