package com.bshpanchuk.testyalantis.common.di

import com.bshpanchuk.testyalantis.presentation.ui.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(named(UI))) }
}
private const val UI = "ui"