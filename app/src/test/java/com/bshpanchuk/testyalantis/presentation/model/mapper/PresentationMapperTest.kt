package com.bshpanchuk.testyalantis.presentation.model.mapper

import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import com.bshpanchuk.testyalantis.presentation.model.RedditPostUI
import com.bshpanchuk.testyalantis.util.TestResourceManager
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

class PresentationMapperTest {

    private lateinit var presentationMapper: PresentationMapper


    @Before
    fun setUp() {
        presentationMapper = PresentationMapper(TestResourceManager())
    }

    @Test
    fun map_success() {
        val dataPost: Long = LocalDateTime.now().minusMonths(1).toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli() / 1000

        val oldItem = ItemRedditPost(
            name = "Name",
            title = "Title",
            author = "Author",
            subreddit = "Subreddit",
            data = dataPost,
            imageUrl = null,
            rating = 5,
            numberOfComments = 12345,
            link = "www.reddit.com/link"
        )

        val expectedItem = RedditPostUI(
            name = "Name",
            title = "Title",
            author = "•Author",
            subreddit = "r/Subreddit",
            data = "1 month ago",
            imageUrl = null,
            rating = "5",
            numberOfComments =  "12.3k",
            link = "www.reddit.com/link"
        )

        val actualItem = presentationMapper.map(oldItem)

        assertThat(actualItem).isEqualTo(expectedItem)
    }

}

