package com.joaoxstone.stoneroyale.core.model.clan

import com.google.gson.annotations.SerializedName

data class MemberList(

    @SerializedName("tag") var tag: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("lastSeen") var lastSeen: String? = null,
    @SerializedName("expLevel") var expLevel: Int? = null,
    @SerializedName("trophies") var trophies: Int? = null,
    @SerializedName("arena") var arena: Arena? = Arena(),
    @SerializedName("clanRank") var clanRank: Int? = null,
    @SerializedName("previousClanRank") var previousClanRank: Int? = null,
    @SerializedName("donations") var donations: Int? = null,
    @SerializedName("donationsReceived") var donationsReceived: Int? = null,
    @SerializedName("clanChestPoints") var clanChestPoints: Int? = null

)