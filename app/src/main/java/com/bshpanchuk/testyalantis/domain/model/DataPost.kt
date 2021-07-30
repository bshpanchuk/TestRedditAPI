package com.bshpanchuk.testyalantis.domain.model

data class DataPost(
    val after: String?,
    val before: String?,
    val itemsPost: List<PostItem>
) {

    data class PostItem(
        val title: String,
        val author: String,
        val subreddit: String,
        val data: String,
        val imageUrl: String?,
        val rating: String,
        val numberOfComments: String,
        val link: String
    )

}