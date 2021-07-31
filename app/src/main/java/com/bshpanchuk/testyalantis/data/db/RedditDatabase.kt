package com.bshpanchuk.testyalantis.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bshpanchuk.testyalantis.data.db.model.ItemRedditDb
import com.bshpanchuk.testyalantis.data.db.model.SubredditRemoteKey

@Database(
    entities = [ItemRedditDb::class, SubredditRemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class RedditDatabase : RoomDatabase() {

    abstract fun postsDao(): RedditPostDao
    abstract fun remoteKeysDao(): SubredditRemoteKeyDao
}