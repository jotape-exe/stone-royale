package com.joaoxstone.stoneroyale.app.model.clan

import com.google.gson.annotations.SerializedName

data class Arena(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null

)