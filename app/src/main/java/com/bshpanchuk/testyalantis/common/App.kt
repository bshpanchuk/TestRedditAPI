package com.bshpanchuk.testyalantis.common

import android.app.Application
import com.bshpanchuk.testyalantis.common.di.applicationModule
import com.bshpanchuk.testyalantis.common.di.networkModule
import com.bshpanchuk.testyalantis.common.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(applicationModule, networkModule, viewModelModule))
        }
    }
}


