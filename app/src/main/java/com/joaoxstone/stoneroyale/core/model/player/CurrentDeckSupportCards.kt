package com.joaoxstone.stoneroyale.core.model.player

import com.google.gson.annotations.SerializedName


data class CurrentDeckSupportCards(

    @SerializedName("name") var name: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("level") var level: Int? = null,
    @SerializedName("maxLevel") var maxLevel: Int? = null,
    @SerializedName("rarity") var rarity: String? = null,
    @SerializedName("count") var count: Int? = null,
    @SerializedName("iconUrls") var iconUrls: IconUrls? = IconUrls()

)