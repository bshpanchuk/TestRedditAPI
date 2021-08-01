package com.bshpanchuk.testyalantis.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bshpanchuk.testyalantis.data.db.model.ItemRedditDb

@Dao
interface RedditPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<ItemRedditDb>)

    @Query("SELECT * FROM items WHERE subreddit = :subreddit ORDER BY indexInResponse ASC")
    fun postsBySubreddit(subreddit: String): PagingSource<Int, ItemRedditDb>

    @Query("DELETE FROM items WHERE subreddit = :subreddit")
    fun deleteBySubreddit(subreddit: String)

}