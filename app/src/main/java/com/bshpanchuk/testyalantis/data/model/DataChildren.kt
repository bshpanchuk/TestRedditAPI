package com.bshpanchuk.testyalantis.data.model


import com.google.gson.annotations.SerializedName

data class DataChildren(
    @SerializedName("author")
    val author: String,
    @SerializedName("category")
    val category: Any?,
    @SerializedName("clicked")
    val clicked: Boolean,
    @SerializedName("created_utc")
    val createdUtc: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_video")
    val isVideo: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("num_comments")
    val numComments: Int,
    @SerializedName("permalink")
    val permalink: String,
    @SerializedName("pinned")
    val pinned: Boolean,
    @SerializedName("preview")
    val preview: Preview,
    @SerializedName("saved")
    val saved: Boolean,
    @SerializedName("score")
    val score: Int,
    @SerializedName("subreddit_name_prefixed")
    val subredditNamePrefixed: String,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String?,
    @SerializedName("visited")
    val visited: Boolean,
)