package com.bshpanchuk.testyalantis.common.di

import android.content.Context
import com.bshpanchuk.testyalantis.domain.model.Credentials
import com.bshpanchuk.testyalantis.common.RedditAuthUtils
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.SharedPrefsStorageManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val redditModule = module {
    factory { SharedPrefsStorageManager(androidContext()) }
    factory { provideTestCredentials(androidContext()) }

    single { provideAuthClient(get(), get()) }
}

private fun provideTestCredentials(context: Context) : Credentials {
    return RedditAuthUtils.loadCredentials(context)
}

private fun provideAuthClient(
    credentials: Credentials,
    prefs: SharedPrefsStorageManager
): RedditAuth {
    return RedditAuth.Builder()
        .setApplicationCredentials(credentials.clientId, credentials.redirectUrl)
        .setScopes(credentials.scopes.toTypedArray())
        .setStorageManager(prefs)
        .setLogging(true)
        .build()
}
