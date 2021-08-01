package com.bshpanchuk.testyalantis.common.di

import com.bshpanchuk.testyalantis.common.BaseResourceManager
import com.bshpanchuk.testyalantis.common.ResourceManager
import com.bshpanchuk.testyalantis.data.db.model.DataReddit
import com.bshpanchuk.testyalantis.data.db.model.ItemRedditDb
import com.bshpanchuk.testyalantis.data.mapper.MapperToDomain
import com.bshpanchuk.testyalantis.data.mapper.MapperToRepo
import com.bshpanchuk.testyalantis.data.model.ResultReddit
import com.bshpanchuk.testyalantis.data.repository.RepositoryImpl
import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import com.bshpanchuk.testyalantis.domain.repository.Repository
import com.bshpanchuk.testyalantis.domain.usecase.GetPostUseCase
import com.bshpanchuk.testyalantis.presentation.model.RedditPostUI
import com.bshpanchuk.testyalantis.presentation.model.mapper.PresentationMapper
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {
    factory { GetPostUseCase(get()) }

    factory<Mapper<ResultReddit, DataReddit>>(named(DATA)) { MapperToRepo() }
    factory<Mapper<ItemRedditDb, ItemRedditPost>>(named(DOMAIN)) { MapperToDomain() }
    factory<Mapper<ItemRedditPost, RedditPostUI>>(named(UI)) { PresentationMapper(get()) }

    factory<BaseResourceManager> { ResourceManager(androidApplication()) }

    single<Repository> { RepositoryImpl(get(), get(), get(named(DATA)), get(named(DOMAIN))) }
}

const val UI = "ui"
const val DOMAIN = "domain"
const val DATA = "data"


