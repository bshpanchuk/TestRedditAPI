package com.bshpanchuk.testyalantis.data.db.model

data class DataReddit(
    val after: String?,
    val before: String?,
    val itemsPost: List<ItemRedditDb>
)
