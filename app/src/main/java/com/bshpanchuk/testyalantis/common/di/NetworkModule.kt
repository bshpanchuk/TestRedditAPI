package com.bshpanchuk.testyalantis.common.di

import com.bshpanchuk.testyalantis.data.api.RedditApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideLogger() }
    factory { provideOkHttpClient(get()) }
    factory { provideApi(get()) }

    single { provideRetrofit(get()) }
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideLogger() : HttpLoggingInterceptor{
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
}

private fun provideOkHttpClient(httpLogInt: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(httpLogInt)
        .build()
}

private fun provideApi(retrofit: Retrofit): RedditApiService = retrofit.create(RedditApiService::class.java)

const val BASE_URL = "https://www.reddit.com/"