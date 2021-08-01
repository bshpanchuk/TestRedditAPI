package com.bshpanchuk.testyalantis.data.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("after")
    val after: String?,
    @SerializedName("before")
    val before: String?,
    @SerializedName("children")
    val children: List<Children>
)