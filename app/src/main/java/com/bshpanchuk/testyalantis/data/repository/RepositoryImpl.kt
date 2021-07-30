package com.bshpanchuk.testyalantis.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.bshpanchuk.testyalantis.data.datasource.GetTopPostRxPagingSource
import com.bshpanchuk.testyalantis.domain.model.DataPost
import com.bshpanchuk.testyalantis.domain.repository.Repository
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi

class RepositoryImpl(
    private val pagingSource: GetTopPostRxPagingSource
) : Repository {

    @ExperimentalCoroutinesApi
    override fun getTopPost(): Flowable<PagingData<DataPost.PostItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false,
                prefetchDistance = 5,
            ),
            pagingSourceFactory = {
                pagingSource
            }
        ).flowable
    }

}