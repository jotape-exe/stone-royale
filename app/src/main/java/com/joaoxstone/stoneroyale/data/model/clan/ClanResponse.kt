package com.joaoxstone.stoneroyale.data.model.clan

import com.google.gson.annotations.SerializedName
data class ClanResponse(

    @SerializedName("tag") var tag: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("badgeId") var badgeId: Int? = null,
    @SerializedName("clanScore") var clanScore: Int? = null,
    @SerializedName("clanWarTrophies") var clanWarTrophies: Int? = null,
    @SerializedName("location") var location: Location? = Location(),
    @SerializedName("requiredTrophies") var requiredTrophies: Int? = null,
    @SerializedName("donationsPerWeek") var donationsPerWeek: Int? = null,
    @SerializedName("clanChestStatus") var clanChestStatus: String? = null,
    @SerializedName("clanChestLevel") var clanChestLevel: Int? = null,
    @SerializedName("clanChestMaxLevel") var clanChestMaxLevel: Int? = null,
    @SerializedName("members") var members: Int? = null,
    @SerializedName("memberList") var memberList: List<MemberList> = arrayListOf()

)