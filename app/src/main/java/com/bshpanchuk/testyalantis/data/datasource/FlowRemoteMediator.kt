package com.bshpanchuk.testyalantis.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bshpanchuk.testyalantis.data.api.RedditApiService
import com.bshpanchuk.testyalantis.data.db.RedditDatabase
import com.bshpanchuk.testyalantis.data.db.RedditPostDao
import com.bshpanchuk.testyalantis.data.db.SubredditRemoteKeyDao
import com.bshpanchuk.testyalantis.data.db.model.DataReddit
import com.bshpanchuk.testyalantis.data.db.model.ItemRedditDb
import com.bshpanchuk.testyalantis.data.db.model.SubredditRemoteKey
import com.bshpanchuk.testyalantis.data.model.ResultReddit
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class FlowRemoteMediator(
    private val redditDatabase: RedditDatabase,
    private val redditApi: RedditApiService,
    private val subredditName: String,
    private val mapper: Mapper<ResultReddit, DataReddit>
) : RemoteMediator<Int, ItemRedditDb>() {
    private val postDao: RedditPostDao = redditDatabase.postsDao()
    private val remoteKeyDao: SubredditRemoteKeyDao = redditDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ItemRedditDb>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                REFRESH -> null
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val remoteKey = redditDatabase.withTransaction {
                        remoteKeyDao.remoteKeyByPost(subredditName)
                    }

                    remoteKey.nextPageKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = redditApi.getTopPost(
                subreddit = subredditName,
                after = loadKey,
                limit = when (loadType) {
                    REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                }
            )

            val dataReddit = mapper.map(response)

            redditDatabase.withTransaction {
                if (loadType == REFRESH) {
                    postDao.deleteBySubreddit(subredditName)
                    remoteKeyDao.deleteBySubreddit(subredditName)
                }

                remoteKeyDao.insert(SubredditRemoteKey(subredditName, dataReddit.after))
                postDao.insertAll(dataReddit.itemsPost)
            }

            return MediatorResult.Success(endOfPaginationReached = dataReddit.itemsPost.isEmpty())
        } catch (e: IOException) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }
}
