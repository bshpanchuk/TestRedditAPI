package com.bshpanchuk.testyalantis.domain.repository

import androidx.paging.PagingData
import com.bshpanchuk.testyalantis.domain.model.DataPost
import io.reactivex.Flowable

interface Repository {

    fun getTopPost(): Flowable<PagingData<DataPost.PostItem>>

}