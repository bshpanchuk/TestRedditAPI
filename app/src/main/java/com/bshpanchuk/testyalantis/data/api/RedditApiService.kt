package com.bshpanchuk.testyalantis.data.api

import com.bshpanchuk.testyalantis.data.model.ResultReddit
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApiService {

    @GET("/r/{subreddit}/top.json")
    fun getTopPost(
        @Path("subreddit") subreddit: String,
        @Query("limit") limit: Int,
        @Query("after") after: String? = null
    ) : Single<ResultReddit>

}