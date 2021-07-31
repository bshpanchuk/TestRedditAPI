package com.bshpanchuk.testyalantis.presentation.model

data class RedditPostUI(
    val name: String,
    val title: String,
    val author: String,
    val subreddit: String,
    val data: String,
    val imageUrl: String?,
    val rating: String,
    val numberOfComments: String,
    val link: String
)