package com.bshpanchuk.testyalantis.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava2.cachedIn
import com.bshpanchuk.testyalantis.domain.usecase.GetPostUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeViewModel(
    getPostUseCase: GetPostUseCase
) : ViewModel() {

    private val data = getPostUseCase
        .invoke()
        .cachedIn(viewModelScope)

    fun getData() = data

}