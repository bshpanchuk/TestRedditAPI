package com.bshpanchuk.testyalantis.common.di

import com.bshpanchuk.testyalantis.presentation.home.HomeViewModel
import com.bshpanchuk.testyalantis.presentation.splach.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { SplashViewModel() }
}