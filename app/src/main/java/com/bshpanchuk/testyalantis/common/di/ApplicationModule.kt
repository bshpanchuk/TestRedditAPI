package com.bshpanchuk.testyalantis.common.di

import com.bshpanchuk.testyalantis.data.datasource.GetTopPostRxPagingSource
import com.bshpanchuk.testyalantis.data.mapper.RedditPostMapper
import com.bshpanchuk.testyalantis.data.model.ResultRedditDTO
import com.bshpanchuk.testyalantis.data.repository.RepositoryImpl
import com.bshpanchuk.testyalantis.domain.model.DataPost
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import com.bshpanchuk.testyalantis.domain.repository.Repository
import com.bshpanchuk.testyalantis.domain.usecase.GetPostUseCase
import org.koin.dsl.module

val applicationModule = module {
    factory { GetPostUseCase(get()) }
    factory<Mapper<ResultRedditDTO, DataPost>> { RedditPostMapper() }

    single<Repository> { RepositoryImpl(get()) }
    single { GetTopPostRxPagingSource(get(), get(), get()) }
}

