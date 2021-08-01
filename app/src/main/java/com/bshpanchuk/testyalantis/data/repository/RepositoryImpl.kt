package com.bshpanchuk.testyalantis.data.repository

import androidx.paging.*
import com.bshpanchuk.testyalantis.data.api.RedditApiService
import com.bshpanchuk.testyalantis.data.datasource.FlowRemoteMediator
import com.bshpanchuk.testyalantis.data.db.RedditDatabase
import com.bshpanchuk.testyalantis.data.db.model.DataReddit
import com.bshpanchuk.testyalantis.data.db.model.ItemRedditDb
import com.bshpanchuk.testyalantis.data.model.ResultReddit
import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import com.bshpanchuk.testyalantis.domain.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val redditDatabase: RedditDatabase,
    private val redditApiService: RedditApiService,
    private val mapper: Mapper<ResultReddit, DataReddit>,
    private val mapperToDomain: Mapper<ItemRedditDb, ItemRedditPost>,
) : Repository {

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun getTopPost(subreddit: String): Flow<PagingData<ItemRedditPost>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            remoteMediator = FlowRemoteMediator(redditDatabase, redditApiService, subreddit, mapper),
            pagingSourceFactory = { redditDatabase.postsDao().postsBySubreddit(subreddit) },
        ).flow.map { pagingData ->
            pagingData.map {
                mapperToDomain.map(it)
            }
        }
    }

}