package com.bshpanchuk.testyalantis.data.repository

import androidx.paging.*
import androidx.paging.rxjava2.flowable
import com.bshpanchuk.testyalantis.data.api.RedditApiService
import com.bshpanchuk.testyalantis.data.datasource.RemoteMediatorRx
import com.bshpanchuk.testyalantis.data.db.RedditDatabase
import com.bshpanchuk.testyalantis.data.db.model.DataReddit
import com.bshpanchuk.testyalantis.data.db.model.ItemRedditDb
import com.bshpanchuk.testyalantis.data.model.ResultReddit
import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import com.bshpanchuk.testyalantis.domain.repository.Repository
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi

class RepositoryImpl(
    private val redditDatabase: RedditDatabase,
    private val redditApiService: RedditApiService,
    private val mapper: Mapper<ResultReddit, DataReddit>,
    private val mapperToDomain: Mapper<ItemRedditDb, ItemRedditPost>,
) : Repository {

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun getTopPost(subreddit: String): Flowable<PagingData<ItemRedditPost>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            remoteMediator = RemoteMediatorRx(redditDatabase, redditApiService, subreddit, mapper),
            pagingSourceFactory = { redditDatabase.postsDao().postsBySubreddit(subreddit) },
        ).flowable.map { pagingData ->
            pagingData.map {
                mapperToDomain.map(it)
            }
        }
    }


}