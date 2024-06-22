package com.joaoxstone.stoneroyale.app.model.player

import com.google.gson.annotations.SerializedName


data class IconUrls(

    @SerializedName("medium") var medium: String? = null,
    @SerializedName("evolutionMedium") var evolutionMedium: String? = null,
    @SerializedName("large") var large: String? = null
)