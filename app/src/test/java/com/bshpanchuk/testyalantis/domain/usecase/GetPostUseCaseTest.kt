package com.bshpanchuk.testyalantis.domain.usecase

import com.bshpanchuk.testyalantis.data.repository.RepositoryImpl
import com.bshpanchuk.testyalantis.domain.repository.Repository
import com.bshpanchuk.testyalantis.util.FakeRepository
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPostUseCaseTest {

    private lateinit var getPostUseCase: GetPostUseCase

    private lateinit var repository: FakeRepository

    @Before
    fun setUp() {
        repository = Mockito.spy(FakeRepository())

        getPostUseCase = GetPostUseCase(repository)
    }

    @Test
    fun getPostUseCase_invoke() {
        val testQuery = "subreddit"

        getPostUseCase(testQuery)

        Mockito.verify(repository).getTopPost(testQuery)
        Mockito.verifyNoMoreInteractions(repository)
    }

    @Test
    fun getPostUseCase_notInvoke() {
        val testQuery = "subreddit"

        getPostUseCase(testQuery)

        Mockito.verify(repository, Mockito.never()).getTopPost("other_query")
    }
}