package com.bshpanchuk.testyalantis.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "items",
        indices = [Index(value = ["subreddit"], unique = false)])
data class ItemRedditDb(
    @PrimaryKey
    val name: String,
    val title: String,
    val author: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val subreddit: String,
    val data: Long,
    val imageUrl: String?,
    val rating: Int,
    val numberOfComments: Int,
    val link: String
) {
    var indexInResponse: Int = -1
}