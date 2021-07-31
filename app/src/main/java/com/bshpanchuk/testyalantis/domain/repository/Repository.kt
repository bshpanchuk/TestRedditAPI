package com.bshpanchuk.testyalantis.domain.repository

import androidx.paging.PagingData
import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import io.reactivex.Flowable

interface Repository {

    fun getTopPost(subreddit: String): Flowable<PagingData<ItemRedditPost>>

}