package com.joaoxstone.stoneroyale.app.model.player

import com.google.gson.annotations.SerializedName


data class CurrentFavouriteCard(

    @SerializedName("name") var name: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("maxLevel") var maxLevel: Int? = null,
    @SerializedName("maxEvolutionLevel") var maxEvolutionLevel: Int? = null,
    @SerializedName("elixirCost") var elixirCost: Int? = null,
    @SerializedName("iconUrls") var iconUrls: IconUrls? = IconUrls(),
    @SerializedName("rarity") var rarity: String? = null

)