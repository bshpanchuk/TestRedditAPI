package com.bshpanchuk.testyalantis.domain.model

data class ItemRedditPost(
    val name: String,
    val title: String,
    val author: String,
    val subreddit: String,
    val data: Long,
    val imageUrl: String?,
    val rating: Int,
    val numberOfComments: Int,
    val link: String
)