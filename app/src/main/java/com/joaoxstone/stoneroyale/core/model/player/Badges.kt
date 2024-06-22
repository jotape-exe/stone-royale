package com.joaoxstone.stoneroyale.core.model.player

import com.google.gson.annotations.SerializedName


data class Badges(

    @SerializedName("name") var name: String? = null,
    @SerializedName("level") var level: Int? = null,
    @SerializedName("maxLevel") var maxLevel: Int? = null,
    @SerializedName("progress") var progress: Int? = null,
    @SerializedName("target") var target: Int? = null,
    @SerializedName("iconUrls") var iconUrls: IconUrls? = IconUrls()

)