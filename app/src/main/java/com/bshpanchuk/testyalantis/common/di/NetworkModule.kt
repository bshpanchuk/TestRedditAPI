package com.bshpanchuk.testyalantis.common.di

import com.bshpanchuk.testyalantis.data.api.RedditApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideApi(get()) }
    single { provideRetrofit(get()) }
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}

private fun provideApi(retrofit: Retrofit): RedditApiService = retrofit.create(RedditApiService::class.java)

const val BASE_URL = "https://www.reddit.com/api/v1/"