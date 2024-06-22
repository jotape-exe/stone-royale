package com.joaoxstone.stoneroyale.core.model.player


import com.google.gson.annotations.SerializedName


data class Achievements(

    @SerializedName("name") var name: String? = null,
    @SerializedName("stars") var stars: Int? = null,
    @SerializedName("value") var value: Int? = null,
    @SerializedName("target") var target: Int? = null,
    @SerializedName("info") var info: String? = null,
    @SerializedName("completionInfo") var completionInfo: String? = null

)