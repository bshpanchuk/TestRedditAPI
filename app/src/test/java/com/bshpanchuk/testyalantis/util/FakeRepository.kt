package com.bshpanchuk.testyalantis.util

import androidx.paging.PagingData
import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import com.bshpanchuk.testyalantis.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class FakeRepository : Repository {

    private val flow = flow<PagingData<ItemRedditPost>> {

    }

    override fun getTopPost(subreddit: String): Flow<PagingData<ItemRedditPost>> {
        return flow
    }
}