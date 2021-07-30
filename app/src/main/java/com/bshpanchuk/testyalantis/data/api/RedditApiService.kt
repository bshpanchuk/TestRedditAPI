package com.bshpanchuk.testyalantis.data.api

import com.bshpanchuk.testyalantis.data.model.ResultRedditDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RedditApiService {

    @Headers("User-Agent: android:com.bshpanchuk.testyalantis:v1.0.0 (by /u/users)")
    @GET("top")
    fun getTopPost(
        @Header("Authorization") token: String,
        @Query("t") time: String,
        @Query("after") after: String?,
        @Query("limit") limit: Int
    ) : Single<ResultRedditDTO>

}