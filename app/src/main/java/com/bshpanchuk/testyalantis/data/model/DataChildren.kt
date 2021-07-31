package com.bshpanchuk.testyalantis.data.model


import com.google.gson.annotations.SerializedName

data class DataChildren(
    @SerializedName("author")
    val author: String,
    @SerializedName("created")
    val created: Long,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("num_comments")
    val numComments: Int,
    @SerializedName("permalink")
    val permalink: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("subreddit")
    val subreddit: String,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String?,
)