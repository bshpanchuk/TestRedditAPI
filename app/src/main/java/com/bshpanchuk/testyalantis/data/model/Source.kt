package com.bshpanchuk.testyalantis.data.model


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)