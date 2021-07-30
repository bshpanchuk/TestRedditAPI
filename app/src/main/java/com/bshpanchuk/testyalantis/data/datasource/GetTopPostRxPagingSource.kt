package com.bshpanchuk.testyalantis.data.datasource

import android.util.Log
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.bshpanchuk.testyalantis.data.api.RedditApiService
import com.bshpanchuk.testyalantis.data.model.ResultRedditDTO
import com.bshpanchuk.testyalantis.domain.model.DataPost
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import com.kirkbushman.auth.RedditAuth
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetTopPostRxPagingSource(
    private val service: RedditApiService,
    private val mapper: Mapper<ResultRedditDTO, DataPost>,
    private val redditAuth: RedditAuth
) : RxPagingSource<String, DataPost.PostItem>() {

    override fun loadSingle(params: LoadParams<String>): Single<LoadResult<String, DataPost.PostItem>> {
        val token = redditAuth.getSavedBearer().getToken()?.accessToken
        return service.getTopPost( "bearer $token","month",  params.key, params.loadSize)
            .subscribeOn(Schedulers.io())
            .map { mapper.map(it) }
            .map { toLoadResult(it) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: DataPost): LoadResult<String, DataPost.PostItem> {
        return LoadResult.Page(
            data = data.itemsPost,
            prevKey = data.before,
            nextKey = data.after
        )
    }

    override fun getRefreshKey(state: PagingState<String, DataPost.PostItem>): String? {
        Log.e("getRefreshKey", state.toString())
        return state.pages[state.anchorPosition ?: 0].nextKey
    }

}