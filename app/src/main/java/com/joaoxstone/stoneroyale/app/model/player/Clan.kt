package com.joaoxstone.stoneroyale.app.model.player

import com.google.gson.annotations.SerializedName


data class Clan(

    @SerializedName("tag") var tag: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("badgeId") var badgeId: Int? = null

)