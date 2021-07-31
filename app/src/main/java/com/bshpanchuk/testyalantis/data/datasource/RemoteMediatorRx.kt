package com.bshpanchuk.testyalantis.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
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

        val loadKey: String? =  when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return Single.just(MediatorResult.Success(true))
            LoadType.APPEND -> {
                val remoteKey = remoteKeyDao.remoteKeyByPost(subredditName)
                if (remoteKey.nextPageKey == null) {
                    return Single.just(MediatorResult.Success(true))
                }
                remoteKey.nextPageKey
            }
        }

        val loadSize = when (loadType) {
            LoadType.REFRESH -> state.config.initialLoadSize
            else -> state.config.pageSize
        }

        return redditApi.getTopPost(
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

    private fun insertToDB(
        loadType: LoadType,
        data: DataReddit
    ) {
        if (loadType == LoadType.REFRESH) {
            postDao.deleteBySubreddit(subredditName)
            remoteKeyDao.deleteBySubreddit(subredditName)
        }

        remoteKeyDao.insert(SubredditRemoteKey(subredditName, data.after))
        postDao.insertAll(data.itemsPost)
    }
}