package com.joaoxstone.stoneroyale.core.model.chest

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("index") var index: Int,
    @SerializedName("name") var name: String
)