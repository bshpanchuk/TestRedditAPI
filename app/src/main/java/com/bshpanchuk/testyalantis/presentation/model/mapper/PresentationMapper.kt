package com.bshpanchuk.testyalantis.presentation.model.mapper

import com.bshpanchuk.testyalantis.R
import com.bshpanchuk.testyalantis.common.BaseResourceManager
import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import com.bshpanchuk.testyalantis.presentation.model.RedditPostUI
import java.util.*

class PresentationMapper(private val resourceManager: BaseResourceManager) : Mapper<ItemRedditPost, RedditPostUI> {

    override fun map(oldData: ItemRedditPost): RedditPostUI {
        return with(oldData) {
            RedditPostUI(
                name = name,
                title = title,
                author = "•$author",
                subreddit = "r/$subreddit",
                data = calcData(data),
                imageUrl = imageUrl,
                rating = formatNumbers(rating),
                numberOfComments = formatNumbers(numberOfComments),
                link = link
            )
        }
    }

    private fun formatNumbers(quantity: Int) : String{
        if (quantity < 1000) return quantity.toString()
        return String.format(Locale.ENGLISH ,"%.1f" , quantity / 1000.0) + "k"
    }

    private fun calcData(createdTime: Long): String {
        val timeMillisPost = createdTime * 1000
        val timeMillisNow = System.currentTimeMillis()

        val timePost = GregorianCalendar().apply {
            timeInMillis = timeMillisPost
        }

        val timeNow = GregorianCalendar().apply {
            timeInMillis = timeMillisNow
        }

        (timeNow.get(Calendar.YEAR) - timePost.get(Calendar.YEAR)).let { years ->
            if (years > 0) return resourceManager.getQuantityString(R.plurals.years_ago, years)
        }

        (timeNow.get(Calendar.MONTH) - timePost.get(Calendar.MONTH)).let { mounts ->
            if (mounts > 0) return resourceManager.getQuantityString(R.plurals.mounts_ago, mounts)
        }

        (timeNow.get(Calendar.DAY_OF_YEAR) - timePost.get(Calendar.DAY_OF_YEAR)).let { days ->
            if (days > 0) return resourceManager.getQuantityString(R.plurals.days_ago, days)
        }

        (timeNow.get(Calendar.HOUR_OF_DAY) - timePost.get(Calendar.HOUR_OF_DAY)).let { hours ->
            if (hours > 0) return resourceManager.getQuantityString(R.plurals.hours_ago, hours)
        }

        val minutes = timeNow.get(Calendar.MINUTE) - timePost.get(Calendar.MINUTE)
        return resourceManager.getQuantityString(R.plurals.minutes_ago, minutes)
    }
}