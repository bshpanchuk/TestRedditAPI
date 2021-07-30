package com.bshpanchuk.testyalantis.common

import android.app.Application
import com.bshpanchuk.testyalantis.common.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    applicationModule,
                    networkModule,
                    viewModelModule,
                    redditModule
                )
            )
        }
    }
}


