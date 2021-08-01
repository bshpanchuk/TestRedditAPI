package com.bshpanchuk.testyalantis.presentation.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import com.bshpanchuk.testyalantis.domain.usecase.GetPostUseCase
import com.bshpanchuk.testyalantis.presentation.model.RedditPostUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class HomeViewModel(
    getPostUseCase: GetPostUseCase,
    private val mapper: Mapper<ItemRedditPost, RedditPostUI>
) : ViewModel() {

    private val pagingData = getPostUseCase
        .invoke(DEFAULT_SUBREDDIT)
        .map { pagingData ->
            pagingData.map {
                mapper.map(it)
            }
        }.cachedIn(viewModelScope)

    fun getData() = pagingData

    companion object {
        const val DEFAULT_SUBREDDIT = "formula1"
    }

}