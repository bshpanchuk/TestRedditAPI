package com.bshpanchuk.testyalantis.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import com.bshpanchuk.testyalantis.domain.usecase.GetPostUseCase
import com.bshpanchuk.testyalantis.presentation.model.RedditPostUI
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeViewModel(
    getPostUseCase: GetPostUseCase,
    private val mapper: Mapper<ItemRedditPost, RedditPostUI>
) : ViewModel() {

    private val subreddit = "formula1"

    private val data = getPostUseCase
        .invoke(subreddit)
        .map { pagingData ->
            pagingData.map {
                mapper.map(it)
            }
        }
        .cachedIn(viewModelScope)


    fun getData() = data

}