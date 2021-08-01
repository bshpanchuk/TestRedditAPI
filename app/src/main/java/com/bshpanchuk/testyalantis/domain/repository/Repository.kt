package com.bshpanchuk.testyalantis.domain.repository

import androidx.paging.PagingData
import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getTopPost(subreddit: String): Flow<PagingData<ItemRedditPost>>

}