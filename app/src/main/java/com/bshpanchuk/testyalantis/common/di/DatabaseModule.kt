package com.bshpanchuk.testyalantis.common.di

import android.app.Application
import androidx.room.Room
import com.bshpanchuk.testyalantis.data.db.RedditDatabase
import com.bshpanchuk.testyalantis.data.db.RedditPostDao
import com.bshpanchuk.testyalantis.data.db.SubredditRemoteKeyDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidApplication()) }

    factory { providePostDao(get()) }
    factory { provideRemoveKeyDao(get()) }
}

private fun provideRemoveKeyDao(redditDatabase: RedditDatabase) : SubredditRemoteKeyDao {
    return redditDatabase.remoteKeysDao()
}

private fun providePostDao(redditDatabase: RedditDatabase) : RedditPostDao {
    return redditDatabase.postsDao()
}

private fun provideDatabase(application: Application) : RedditDatabase {
    return Room
        .databaseBuilder(application, RedditDatabase::class.java, "RedditDatabase")
        .build()
}