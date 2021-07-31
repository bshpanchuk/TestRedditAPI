package com.bshpanchuk.testyalantis.data.model


import com.google.gson.annotations.SerializedName

data class Children(
    @SerializedName("data")
    val data: DataChildren
)