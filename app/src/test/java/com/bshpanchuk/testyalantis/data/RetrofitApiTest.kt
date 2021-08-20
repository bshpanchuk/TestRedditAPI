package com.bshpanchuk.testyalantis.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bshpanchuk.testyalantis.common.di.BASE_URL
import com.bshpanchuk.testyalantis.data.api.RedditApiService
import com.bshpanchuk.testyalantis.util.MainCoroutineRule
import com.bshpanchuk.testyalantis.util.MockResponseFileReader
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RetrofitApiTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mockWebServer: MockWebServer

    private lateinit var redditApiService: RedditApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        redditApiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RedditApiService::class.java)
    }

    @Test
    fun test1() = mainCoroutineRule.runBlockingTest {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("response_success.json").content)
        mockWebServer.enqueue(response)

        val  actualResponse = redditApiService.getTopPost("formula1", 25)

        assertThat(actualResponse.toString().contains("200")).isEqualTo(response.toString().contains("200"))
    }

    @Test
    fun readSuccessResponse_success(){
        val reader = MockResponseFileReader("response_success.json")
        assertThat(reader).isNotNull()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}