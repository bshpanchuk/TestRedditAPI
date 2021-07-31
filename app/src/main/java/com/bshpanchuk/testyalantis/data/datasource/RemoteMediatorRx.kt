package com.bshpanchuk.testyalantis.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.bshpanchuk.testyalantis.data.api.RedditApiService
import com.bshpanchuk.testyalantis.data.db.RedditDatabase
import com.bshpanchuk.testyalantis.data.db.RedditPostDao
import com.bshpanchuk.testyalantis.data.db.SubredditRemoteKeyDao
import com.bshpanchuk.testyalantis.data.db.model.DataReddit
import com.bshpanchuk.testyalantis.data.db.model.ItemRedditDb
import com.bshpanchuk.testyalantis.data.db.model.SubredditRemoteKey
import com.bshpanchuk.testyalantis.data.model.ResultReddit
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@ExperimentalPagingApi
class RemoteMediatorRx(
    db: RedditDatabase,
    private val redditApi: RedditApiService,
    private val subredditName: String,
    private val mapper: Mapper<ResultReddit, DataReddit>
) : RxRemoteMediator<Int, ItemRedditDb>() {
    private val postDao: RedditPostDao = db.postsDao()
    private val remoteKeyDao: SubredditRemoteKeyDao = db.remoteKeysDao()

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, ItemRedditDb>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    REFRESH -> "null"
                    PREPEND -> "return"
                    APPEND -> {
                        val remoteKey = remoteKeyDao.remoteKeyByPost(subredditName)
                        remoteKey.nextPageKey ?: "return"
                    }
                }
            }.flatMap { page ->
                when(page){
                    "return" -> {
                        Single.just(MediatorResult.Success(endOfPaginationReached = true))
                    }
                    else -> {
                        val loadSize = when (loadType) {
                            REFRESH -> state.config.initialLoadSize
                            else -> state.config.pageSize
                        }

                        val loadKey = if (page != "null") page else null

                        redditApi.getTopPost(
                            subreddit = subredditName,
                            limit = loadSize,
                            after = loadKey
                        )
                            .subscribeOn(Schedulers.io())
                            .map { mapper.map(it) }
                            .map {
                                insertToDB(loadType, it)
                                it.itemsPost
                            }
                            .observeOn(AndroidSchedulers.mainThread())
                            .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.isEmpty()) }
                            .onErrorReturn { MediatorResult.Error(it) }
                    }
                }

            }.onErrorReturn { MediatorResult.Success(endOfPaginationReached = true) }
    }

    private fun insertToDB(
        loadType: LoadType,
        data: DataReddit
    ) {
        if (loadType == REFRESH) {
            postDao.deleteBySubreddit(subredditName)
            remoteKeyDao.deleteBySubreddit(subredditName)
        }

        remoteKeyDao.insert(SubredditRemoteKey(subredditName, data.after))
        postDao.insertAll(data.itemsPost)
    }
}