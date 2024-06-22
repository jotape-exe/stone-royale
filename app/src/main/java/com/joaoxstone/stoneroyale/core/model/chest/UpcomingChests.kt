package com.joaoxstone.stoneroyale.core.model.chest

import com.google.gson.annotations.SerializedName


data class UpcomingChests(
    @SerializedName("items") var items: ArrayList<Item> = arrayListOf()
)