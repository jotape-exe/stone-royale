package com.joaoxstone.stoneroyale.core.model.clan

import com.google.gson.annotations.SerializedName


data class Location(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("isCountry") var isCountry: Boolean? = null,
    @SerializedName("countryCode") var countryCode: String? = null

)