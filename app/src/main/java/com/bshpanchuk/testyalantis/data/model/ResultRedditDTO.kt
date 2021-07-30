package com.bshpanchuk.testyalantis.data.model


import com.google.gson.annotations.SerializedName

data class ResultRedditDTO(
    @SerializedName("data")
    val data: Data,
    @SerializedName("kind")
    val kind: String
)