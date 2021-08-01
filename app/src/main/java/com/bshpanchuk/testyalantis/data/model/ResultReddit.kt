package com.bshpanchuk.testyalantis.data.model

import com.google.gson.annotations.SerializedName

data class ResultReddit(
    @SerializedName("data")
    val data: Data
)